class NestedAlpha { run(): void {} }
class NestedBeta { run(): void {} }
function useNestedAlias() {
  let item: NestedAlpha = new NestedAlpha();
  const change = () => { item = new NestedBeta() as any; };
  { const alias = change; alias(); }
  item.run();
}
