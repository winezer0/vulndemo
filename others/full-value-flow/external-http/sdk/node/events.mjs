import assert from "node:assert/strict";
import http from "node:http";
import { once } from "node:events";

const server = http.createServer((request, response) => {
  if (request.url === "/failure") {
    request.socket.destroy();
    return;
  }
  response.writeHead(200);
  response.write(request.url);
  response.end(":body");
});
server.listen(0, "127.0.0.1");
await once(server, "listening");
const base = `http://127.0.0.1:${server.address().port}`;

function body(path) {
  return new Promise((resolve, reject) => {
    http.get(base + path, response => {
      const chunks = [];
      response.on("data", chunk => {
        assert.ok(Buffer.isBuffer(chunk));
        chunks.push(chunk);
      });
      response.on("end", (...args) => {
        assert.equal(args.length, 0);
        resolve(Buffer.concat(chunks).toString());
      });
      response.on("error", reject);
    }).on("error", reject);
  });
}

try {
  assert.deepEqual(await Promise.all([body("/first"), body("/second")]), ["/first:body", "/second:body"]);
  let responseCalled = false;
  await assert.rejects(new Promise((resolve, reject) => {
    http.get(`${base}/failure`, () => { responseCalled = true; resolve(); }).on("error", reject);
  }));
  assert.equal(responseCalled, false);
  console.log(JSON.stringify({ runtime: process.version, cases: 3, status: "passed" }));
} finally {
  server.closeAllConnections();
  await new Promise(resolve => server.close(resolve));
}
