function sink(value: string) {}

export function sameLine(input: string) {
  sink(input); sink(input);
}
