
export function Source(): string { return "value"; }
export function Sink(value: string): void { console.log(value); }
export function Driver(input: string): void {
  const direct = input;
  const complex = input + "!";
  Sink(direct);
  Sink(complex);
}
