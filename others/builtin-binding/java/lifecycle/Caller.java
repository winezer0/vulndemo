package lexical;
import static lexical.Library.choose;
class Caller {
 Service slot;
 void run(Service slot) {
  slot.hit(); // target:Service.hit
  { Other slot = new Other(); slot.hit(); } // target:Other.hit
  slot.hit(); // target:Service.hit
  choose(1); // target:Library.choose
 }
 int pick() { return 0; }
 int pick(int n) { return n; }
 int picks(int pick) {
  return pick() + this.pick(1); // targets:Caller.pick/0,Caller.pick/1
 }
 void pattern(Object o) {
  if (o instanceof Service svc && svc.ready()) { svc.hit(); } // targets:Service.ready,Service.hit
 }
 void incomplete(Service receiver) {
  receiver.hit(); // target:Service.hit
 }
 int values(int x) { int z = pick(x); consume(z); return pick(); }
 void consume(int value) {}
 class Inner { int nested() { return pick(); } }
}
