export function plain() {
  parseInt('1'); // lexical:absent
}
function parameter(parseInt: (value: string) => number) {
  parseInt('1'); // lexical:bound
}
function lexical() {
  parseInt('1'); // lexical:uninitialized
  let parseInt = 0;
  parseInt('1'); // lexical:bound
}
function variable() {
  parseInt('1'); // lexical:bound
  var parseInt = 0;
}
function sibling() {
  { let parseInt = 0; }
  parseInt('1'); // lexical:absent
}
function hoisted() {
  parseInt('1'); // lexical:bound:function
  function parseInt(value: string) { return value; }
}
function destructured(value: any) {
  const {key: parseInt} = value;
  parseInt('1'); // lexical:bound
}
function loop(values: number[]) {
  for (let parseInt of values) {
    parseInt('1'); // lexical:bound
  }
  parseInt('1'); // lexical:absent
}
function mutation() {
  parseInt('1'); // lexical:absent
  var size = 0; // mutation binding
}
function erased() {
  type parseInt = number;
  parseInt('1'); // lexical:absent
}
