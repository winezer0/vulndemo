class Alpha { void ping() {} }
class Beta { void ping() {} }
class Use {
 void first() { Alpha worker = new Alpha(); worker.ping(); }
 void second() { Beta worker = new Beta(); worker.ping(); }
}
class A { int x; }
class B { int x; }
class OuterA {
 class Inner {
  A p;
  int read() { A q = this.p; return p.x; }
 }
}
class OuterB {
 class Inner {
  B p;
  int read() { B q = this.p; return p.x; }
 }
}
