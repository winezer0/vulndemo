export function source(): {data: {value: string}} {
  return {data: {value: 'ok'}};
}

export function use() {
  const direct = source();
  const {data: {value: alias = 'fallback'}} = source();
  return alias;
}
