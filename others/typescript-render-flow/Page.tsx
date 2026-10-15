function sink(value: string) {}

export function Page(input: string) {
  function Inner() { return <Badge />; }
  sink(input);
  return <Header />;
}
