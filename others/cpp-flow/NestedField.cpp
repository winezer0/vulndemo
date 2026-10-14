class NestedChild { public: int value; int run() { return value; } int send(int item) { return item; } };
class NestedParent { public: NestedChild next; };
int readNested(NestedParent parent) { return parent.next.value; }
void writeNested(NestedParent parent) { parent.next.value = 2; }
int callNested(NestedParent parent) { return parent.next.run(); }
int flowNested(NestedParent parent, int item) { int output = parent.next.send(item); return output; }
