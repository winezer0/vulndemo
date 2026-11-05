import axios from "axios";

export function callback(thenInput) {
  return axios.get(thenInput).then(response => {
    const thenOut = response.data.value;
    return thenOut;
  });
}

export function otherCallback(otherInput) {
  return axios.get(otherInput).then(response => {
    const otherThenOut = response.data.value;
    return otherThenOut;
  });
}

export function twice(chainInput) {
  return axios.get(chainInput).then(response => response.data).then(data => {
    const chainOut = data.value;
    return chainOut;
  });
}

export function threeTimes(tripleInput) {
  return axios.get(tripleInput).then(response => response.data).then(data => data.value).then(value => {
    const tripleOut = value;
    return tripleOut;
  });
}

export function fetchChain(fetchChainInput) {
  return fetch(fetchChainInput).then(response => response.json()).then(data => {
    const fetchChainOut = data.value;
    return fetchChainOut;
  });
}

async function fetchForward(url) {
  return fetch(url);
}

export async function fetchAcross(fetchAcrossInput) {
  const response = await fetchForward(fetchAcrossInput);
  const { value } = await response.json();
  const fetchAcrossOut = value;
  return fetchAcrossOut;
}
