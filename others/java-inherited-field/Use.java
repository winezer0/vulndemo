class Base { int x; }
class Sub extends Base {
 void sink(int v) {}
 void run() { this.x++; sink(this.x); }
}
