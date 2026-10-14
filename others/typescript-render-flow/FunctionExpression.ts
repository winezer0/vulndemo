function outer(secret: string) {
  const nested = function (value: string) {
    sink(secret, value);
    return value;
  };
  return nested(secret);
}
