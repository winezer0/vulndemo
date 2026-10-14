class MutableAlpha { run(): void {} }
class MutableBeta { run(): void {} }
function useMutableAlias() {
  let item: MutableAlpha = new MutableAlpha();
  const change = () => { item = new MutableBeta() as any; };
  let alias = change;
  alias();
  item.run();
}
