import axios from "axios";
import http from "node:http";
import https from "node:https";

export async function fetchBody(fetchInput) {
  const response = await fetch(fetchInput);
  const fetchOut = await response.text();
  return fetchOut;
}

export async function axiosBody(axiosInput) {
  const response = await axios.get(axiosInput);
  const axiosOut = response.data;
  return axiosOut;
}

export function httpBody(httpInput) {
  http.get(httpInput, response => {
    response.on("data", chunk => {
      const httpOut = chunk;
      consume(httpOut);
    });
  });
}

export function httpsBody(httpsInput) {
  https.get(httpsInput, response => {
    response.on("data", chunk => {
      const httpsOut = chunk;
      consume(httpsOut);
    });
  });
}

function consume(value) { return value; }

export function httpEvents(eventInput, eventName, isolatedInput) {
  http.get(eventInput, response => {
    response.on("data", chunk => { const eventDataOut = chunk; consume(eventDataOut); });
    response.on("error", error => { const eventErrorOut = error; consume(eventErrorOut); });
    response.on(eventName, payload => { const eventUnknownOut = payload; consume(eventUnknownOut); });
    response.on("end", () => { const eventEndOut = "ended"; consume(eventEndOut); });
  });
  http.get(isolatedInput, other => {
    other.on("data", chunk => { const eventIsolatedOut = chunk; consume(eventIsolatedOut); });
  });
}

export function httpConditionalCancel(cancelInput, shouldCancel) {
  const request = http.get(cancelInput, response => {
    response.on("data", chunk => { const cancelOut = chunk; consume(cancelOut); });
  });
  if (shouldCancel) request.destroy();
}
