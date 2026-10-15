class Box { public: int value; };
void first(Box& box) { box.value = 1; } void second(Box& box) { int n = box.value; }
void sink(int value) {}
void run(int a, int b) { sink(a); sink(a + b); }
int runLambda(int input) { auto fn = [&]() { return input; }; return fn(); }
void withDefault(int item = 42) { sink(item); }
struct Pair { int left; int right; }; Pair makePair(int left, int right) { return Pair{left, right}; }
template<int N, class T = int, std::integral U> struct TemplateBox {};
namespace ns { class Trusted; } class Vault { friend class ns::Trusted; };
