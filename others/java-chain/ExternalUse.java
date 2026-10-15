class DecoyBox { int x; }
class DecoyHolder { DecoyBox box; }
class ExternalUse {
  DecoyHolder makeHolder() { return new DecoyHolder(); }
  void sink(int value) {}
  void run(ExternalSupplier supplier) { sink(supplier.makeHolder().box.x); supplier.makeHolder().box.send(); }
  void write(ExternalSupplier supplier) { supplier.makeHolder().box.x = 1; supplier.makeHolder().box.x++; }
  void forbidden(ExternalSupplier supplier) { supplier.makeHolder().box.hidden(); }
}
