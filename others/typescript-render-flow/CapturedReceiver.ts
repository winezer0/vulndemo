class CapturedAlpha { value = 1; run(): void {} }
class CapturedBeta { value = 2; run(): void {} }

function captured(flag: boolean) {
  let item: CapturedAlpha = new CapturedAlpha();
  if (flag) { item = new CapturedBeta() as any; }
  const read = () => { item.run(); return item.value; };
  const shadow = (item: CapturedAlpha) => item.value;
  return read();
}
