function nestedDefaultCapture(secret: string) {
  return (fn = () => secret) => fn();
}
