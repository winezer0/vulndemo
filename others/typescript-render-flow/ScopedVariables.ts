function firstVariable() {
  class Box { value = 1; }
  const b = new Box();
  b.value = 3;
  return b.value;
}
function secondVariable() {
  class Box { value = 2; }
  const b = new Box();
  b.value = 4;
  return b.value;
}
