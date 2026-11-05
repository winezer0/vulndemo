import axios, { AxiosResponse } from "axios";

type Payload = { value: string };
type ResponseAlias = AxiosResponse<Payload>;

export async function destructured(aliasInput: string) {
  const response: ResponseAlias = await axios.get(aliasInput);
  const { data: aliasData } = response;
  const aliasOut = aliasData.value;
  return aliasOut;
}

export function callback(thenInput: string) {
  return axios.get<Payload>(thenInput).then(response => {
    const thenOut = response.data.value;
    return thenOut;
  });
}

export function otherCallback(otherInput: string) {
  return axios.get<Payload>(otherInput).then(response => {
    const otherThenOut = response.data.value;
    return otherThenOut;
  });
}

export function twice(chainInput: string) {
  return axios.get<Payload>(chainInput).then(response => response.data).then(data => {
    const chainOut = data.value;
    return chainOut;
  });
}

export function threeTimes(tripleInput: string) {
  return axios.get<Payload>(tripleInput).then(response => response.data).then(data => data.value).then(value => {
    const tripleOut = value;
    return tripleOut;
  });
}

export function fetchChain(fetchChainInput: string) {
  return fetch(fetchChainInput).then(response => response.json()).then(data => {
    const fetchChainOut = data.value;
    return fetchChainOut;
  });
}

async function fetchForward(url: string) {
  return fetch(url);
}

export async function fetchAcross(fetchAcrossInput: string) {
  const response = await fetchForward(fetchAcrossInput);
  const { value } = await response.json() as Payload;
  const fetchAcrossOut = value;
  return fetchAcrossOut;
}
