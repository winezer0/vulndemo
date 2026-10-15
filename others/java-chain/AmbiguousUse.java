class AmbiguousSupplier {
  ExternalHolder makeHolder(String value) { return new ExternalHolder(); }
  DecoyHolder makeHolder(Integer value) { return new DecoyHolder(); }
}
class AmbiguousUse {
  void sink(int value) {}
  void run(AmbiguousSupplier supplier, Object value) { sink(supplier.makeHolder(value).box.x); }
}
