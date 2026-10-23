#include "types.hpp"
int invoke(Alpha &client, int input) {
    int first = client.hit(input) + client.value;
    {
        Beta client{};
        int nested = client.hit(input) + client.value;
    }
    return client.hit(first);
}
