class UnknownSupplier {
  MissingHolder makeHolder() { return null; }
}
class UnknownUse {
  void sink(int value) {}
  void run(UnknownSupplier supplier) { sink(supplier.makeHolder().box.x); }
}
