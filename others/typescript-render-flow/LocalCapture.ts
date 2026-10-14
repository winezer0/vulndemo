class Box { value = 0; }
export function outer(){ const b: Box = new Box(); const inner=()=>{b.value=1}; }
