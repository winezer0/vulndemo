function defaultCapture(secret: string) {
  return ({item = secret}: {item?: string}) => item;
}
