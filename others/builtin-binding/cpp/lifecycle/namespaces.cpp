namespace A { int choose(int input) { return input; } }
namespace B { int choose(int input) { return input + 1; } }
namespace Pick = A;
int alias(int input) { return Pick::choose(input); }
int imported(int input) { using A::choose; return choose(input); }
int directed(int input) { using namespace B; return choose(input); }
int choose(int input) { return input; }
int shadow(int (*choose)(int), int input) { return choose(input); }
int overloaded(int input) { return input; }
int overloaded(int left, int right) { return left + right; }
int overloads(int input) { return overloaded(input) + overloaded(input, input); }
