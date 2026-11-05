import axios from "axios";

export async function fetchStream(streamInput, streamOptions) {
  const response = await fetch(streamInput, streamOptions);
  const reader = response.body.getReader();
  const chunk = await reader.read();
  const streamOut = chunk.value;
  return streamOut;
}

export async function fetchPlainStream(plainStreamInput) {
  const plainStreamResponse = await fetch(plainStreamInput);
  const reader = plainStreamResponse.body.getReader();
  const chunk = await reader.read();
  const plainStreamOut = chunk.value;
  return plainStreamOut;
}

// The binary consumer uses an actual awaited Response rather than a Promise receiver.
export async function binaryFetch(binaryInput) {
  const response = await fetch(binaryInput);
  const binaryOut = await response.arrayBuffer();
  return binaryOut;
}

// An unawaited Promise must not acquire Response receiver semantics.
export async function pendingFetch(pendingInput) {
  const pending = fetch(pendingInput);
  const pendingOut = pending.text();
  return pendingOut;
}

// Response data can have unknown keys even after a modeled JSON decoder.
export async function decodedFetch(decodedInput, key) {
  const response = await fetch(decodedInput);
  const value = await response.json();
  const decodedOut = value[key];
  return decodedOut;
}

// Custom options keep the operation outside the finite single-argument contract.
export async function configuredFetch(configuredInput, options) {
  const response = await fetch(configuredInput, options);
  const configuredOut = await response.text();
  return configuredOut;
}

// User transforms retain unknown request-result and property dependencies.
export async function transformedAxios(axiosConfigInput, transform) {
  const configuredResponse = await axios.get(axiosConfigInput, { transformResponse: [transform] });
  const axiosConfigOut = configuredResponse.data;
  return axiosConfigOut;
}
