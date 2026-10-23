#include "types.hpp"
int capture(Alpha client) {
    auto action = [copy = client](int input) { return copy.hit(input); };
    return action(1);
}
