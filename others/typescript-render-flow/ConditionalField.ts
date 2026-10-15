class Left { value = 1; }
class Right { value = 2; }

function choose(flag: boolean) {
  let item: Left = new Left();
  const before = item.value;
  if (flag) { item = new Right() as any; }
  return item.value;
}
