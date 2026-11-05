import assert from "node:assert/strict";
import http from "node:http";
import { once } from "node:events";
import { createRequire } from "node:module";
import { join } from "node:path";

const requireSDK = createRequire(join(process.env.HTTP_SDK_NODE_ROOT, "package.json"));
const axios = requireSDK("axios");
assert.equal(requireSDK("axios/package.json").version, "1.7.7");

const expected = JSON.stringify({ payload: "fixed-response", independent: "clean" });
const server = http.createServer((request, response) => {
  if (request.url === "/failure") { request.socket.destroy(); return; }
  response.writeHead(201, { "Content-Type": "application/json" });
  response.write(expected.slice(0, 13));
  response.end(expected.slice(13));
});
server.listen(0, "127.0.0.1");
await once(server, "listening");
const base = `http://127.0.0.1:${server.address().port}`;
try {
  const textResponse = await fetch(`${base}/request-marker`);
  assert.equal(textResponse.status, 201);
  assert.equal(await textResponse.text(), expected);
  const jsonResponse = await fetch(base);
  assert.deepEqual(await jsonResponse.json(), JSON.parse(expected));
  const binaryResponse = await fetch(base);
  assert.equal(Buffer.from(await binaryResponse.arrayBuffer()).toString(), expected);
  const streamResponse = await fetch(base);
  const chunks = [];
  for await (const chunk of streamResponse.body) chunks.push(Buffer.from(chunk));
  assert.equal(Buffer.concat(chunks).toString(), expected);
  const reader = (await fetch(base)).body.getReader();
  const firstRead = await reader.read();
  assert.equal(firstRead.done, false);
  assert.ok(Buffer.from(firstRead.value).length > 0);
  await reader.cancel();
  const pending = fetch(base);
  assert.equal(pending.text, undefined);
  await (await pending).body.cancel();
  const controller = new AbortController();
  controller.abort();
  await assert.rejects(fetch(base, { signal: controller.signal }), { name: "AbortError" });
  const nodeBody = await new Promise((resolve, reject) => {
    http.get(base, response => {
      const parts = [];
      response.on("data", chunk => parts.push(chunk));
      response.on("end", () => resolve(Buffer.concat(parts).toString()));
      response.on("error", reject);
    }).on("error", reject);
  });
  assert.equal(nodeBody, expected);
  const axiosResponse = await axios.get(base, { proxy: false });
  assert.equal(axiosResponse.status, 201);
  assert.deepEqual(axiosResponse.data, JSON.parse(expected));
  const transformed = await axios.get(base, { proxy: false, transformResponse: [() => "custom-content"] });
  assert.equal(transformed.data, "custom-content");
  const customClient = axios.create({ proxy: false });
  customClient.interceptors.response.use(response => {
    response.data = "replacement-content";
    return response;
  });
  assert.equal((await customClient.get(base)).data, "replacement-content");
  assert.deepEqual((await axios.get(base, { proxy: false })).data, JSON.parse(expected));
  const loopClient = axios.create({ proxy: false });
  const loopOutputs = [];
  for (let index = 0; index < 2; index++) {
    loopOutputs.push((await loopClient.get(base)).data);
    loopClient.interceptors.response.use(response => { response.data = "loop-replacement"; return response; });
  }
  assert.deepEqual(loopOutputs, [JSON.parse(expected), "loop-replacement"]);
  await assert.rejects(axios.get(`${base}/failure`, { proxy: false }));
  console.log(JSON.stringify({ runtime: process.version, axios: "1.7.7", cases: 14, status: "passed" }));
} finally {
  server.closeAllConnections();
  await new Promise(resolve => server.close(resolve));
}
