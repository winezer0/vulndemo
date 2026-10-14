export function outer(input: string) {
  return () => {
    const value = input;
    { const input = 'inner'; input.toString(); }
    return value;
  };
}
