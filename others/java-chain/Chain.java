class Box { int x; void send() {} }
class Holder { Box box; }
class Chain {
  void sink(int value) {}
  void run(Holder h) { sink(h.box.x); h.box.send(); }
}
