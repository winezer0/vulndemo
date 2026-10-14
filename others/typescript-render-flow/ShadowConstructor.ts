class ShadowBox { shadowedRun(): void {} }
export function callShadowed(ShadowBox: new () => any) {
  const item = new ShadowBox();
  item.shadowedRun();
}
