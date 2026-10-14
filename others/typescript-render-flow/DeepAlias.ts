class DeepAlpha { run(): void {} }
class DeepBeta { run(): void {} }
function useDeepAlias() {
  let item: DeepAlpha = new DeepAlpha();
  const change = () => { item = new DeepBeta() as any; };
  { const alias = change; { const next = alias; next(); } }
  item.run();
}
