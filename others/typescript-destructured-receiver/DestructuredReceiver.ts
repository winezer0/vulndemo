export class Child { value = "child"; }
export class Box { x: Child = new Child(); value = "box"; }
export function driver(box: Box) {
  const {x}: Box = box;
  return x.value;
}
