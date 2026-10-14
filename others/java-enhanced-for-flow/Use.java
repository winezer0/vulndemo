class Box { int x; }
class Other { int x; }
class Use {
  void sink(int value) {}
  void run(Box[] items) {
    Other p = new Other();
    for (Box p : items) { sink(p.x); }
    sink(p.x);
  }
}
