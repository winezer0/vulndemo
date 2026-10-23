class Calls {
 int value;
 int pick() { return 0; }
 int pick(int n) { return n; }
 int run(int value) {
  int a = pick();
  int b = this.pick(1);
  return value + this.value + a + b;
 }
}
