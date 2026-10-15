export function first(): string | null {
  return null;
}

export function second(): string {
  return "fallback";
}

export function choose(input: string | null): string {
  return input ?? second();
}

export function chooseCalls(): string {
  return first() ?? second();
}

export function chooseWrapped(flag: boolean, input: string | null): string {
  return (flag ? input : second()) as string;
}

export function chooseSequence(): string {
  return (first(), second());
}

export function chooseSelected(): string {
  const selected = (first(), second());
  return selected;
}
