class Use {
  void sink(int v) {}
  void run(int x) { sink(x); sink(x + 1); }
}
