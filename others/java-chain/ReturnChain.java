class ReturnBox { int x; void send() {} }
class ReturnHolder { ReturnBox box; }
class ReturnChain {
  ReturnHolder makeHolder() { return new ReturnHolder(); }
  void sink(int value) {}
  void run() { sink(makeHolder().box.x); makeHolder().box.send(); }
}
class ReturnSupplier { ReturnHolder makeHolder() { return new ReturnHolder(); } }
class ReturnUse {
  void sink(int value) {}
  void run(ReturnSupplier supplier) { sink(supplier.makeHolder().box.x); supplier.makeHolder().box.send(); }
}
