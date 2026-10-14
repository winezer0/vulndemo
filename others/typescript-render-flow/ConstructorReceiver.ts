class ConstructorReceiverAlpha { constructRun(): void {} }
export function use() {
  let item = new ConstructorReceiverAlpha();
  item.constructRun();
}
