import axios from "axios";

export async function intercept(interceptInput, replacement, choose) {
  const client = axios.create();
  const other = axios.create();
  const early = await client.get(interceptInput);
  const earlyOut = early.data;
  client.interceptors.response.use(response => {
    response.data = replacement;
    return response;
  });
  const response = await client.get(interceptInput);
  const interceptOut = response.data;
  const alias = client;
  const aliasResponse = await alias.get(interceptInput);
  const aliasOut = aliasResponse.data;
  const selected = choose ? client : other;
  const selectedResponse = await selected.get(interceptInput);
  const selectedOut = selectedResponse.data;
  const independent = await other.get(interceptInput);
  const otherOut = independent.data;
  let timedAlias = other; const timedBefore = await timedAlias.get(interceptInput); timedAlias = client; const timedAfter = await timedAlias.get(interceptInput);
  const timedBeforeOut = timedBefore.data;
  const timedAfterOut = timedAfter.data;
  return interceptOut;
}

// loopIntercept can use a callback registered in a previous iteration.
export async function loopIntercept(loopInput, loopReplacement) {
  const client = axios.create();
  for (let index = 0; index < 2; index++) {
    const response = await client.get(loopInput);
    const loopOut = response.data;
    void loopOut;
    client.interceptors.response.use(value => { value.data = loopReplacement; return value; });
  }
}
