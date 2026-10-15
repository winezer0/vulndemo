class Outer { value = 1; }
export function use(kind: number) {
  let item: Outer = new Outer();
  switch (kind) {
    case 0: class item {} item.value; break;
    default: item.value;
  }
  item.value;
}
