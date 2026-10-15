function sink(value: number) {}
export function outer() {
  const inner = () => { sink(1); };
  sink(2);
}
export function compact(){ const inner=()=>{sink(3)};sink(4) }
class Box { value = 0; }
export function fieldFlow(b: Box){ const inner=()=>{sink(b.value)};sink(b.value) }
