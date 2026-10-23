export function plain() {
  parseInt('1'); // lexical:absent
}
function parameter(parseInt) {
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
  function parseInt(value) { return value; }
}
function destructured(value) {
  const {key: parseInt} = value;
  parseInt('1'); // lexical:bound
}
function loop(values) {
  for (let parseInt of values) {
    parseInt('1'); // lexical:bound
  }
  parseInt('1'); // lexical:absent
}
function mutation() {
  parseInt('1'); // lexical:absent
  var size = 0; // mutation binding
}
