template <typename T> int dependent(T &value, int input) { return value.hit(input); }
int indirect(int (*action)(int), int input) { return action(input); }
struct Fun { int operator()(int input) { return input; } };
int callable(Fun action, int input) { return action(input); }
#define expanded(input) missing(input)
int macro(int input) { return expanded(input); }
namespace Associated { struct Token {}; int discover(Token token) { return 1; } }
int adl(Associated::Token token) { return discover(token); }
struct Left { int hit(int input) { return input; } };
struct Right { int hit(int input) { return input + 1; } };
struct Ambiguous : Left, Right {};
int inherited(Ambiguous &value, int input) { return value.hit(input); }
template <typename T> int identity(T value) { return value; }
int instantiated(int input) { return identity<int>(input); }
