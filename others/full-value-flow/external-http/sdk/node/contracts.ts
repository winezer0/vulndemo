import axios, { AxiosResponse } from "axios";
import { get, IncomingMessage } from "node:http";

// Compile the actual SDK carriers and ensure Promise does not expose Response consumers.
export async function sdkContracts(url: string): Promise<string> {
  const pending: Promise<Response> = fetch(url);
  // @ts-expect-error A pending Promise has no Response.text method.
  pending.text();
  const response: Response = await pending;
  const text: string = await response.text();
  const binary: ArrayBuffer = await (await fetch(url)).arrayBuffer();
  const json: unknown = await (await fetch(url)).json();
  const result: AxiosResponse<{ payload: string }> = await axios.get(url);
  get(url, (message: IncomingMessage) => {
    message.on("data", (chunk: Buffer) => { void chunk.byteLength; });
    message.on("end", () => { void message.statusCode; });
  });
  void binary;
  void json;
  return text + result.data.payload;
}
