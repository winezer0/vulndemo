class OverloadBox { int x; }
class OverloadOtherBox { int x; }
class OverloadHolder { OverloadBox box; }
class OverloadOtherHolder { OverloadOtherBox box; }
class OverloadChain {
  OverloadHolder makeHolder(String value) { return new OverloadHolder(); }
  OverloadOtherHolder makeHolder(int value) { return new OverloadOtherHolder(); }
  void sink(int value) {}
  void run() {
    sink(makeHolder("text").box.x);
    sink(makeHolder(1).box.x);
  }
}
