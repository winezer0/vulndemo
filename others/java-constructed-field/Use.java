class Box { int x; }
class Other { int x; }
class Use {
  int run() { return new Box().x; }
}
