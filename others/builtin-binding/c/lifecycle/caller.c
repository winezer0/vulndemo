#include "api.h"
int invoke(struct Alpha *client, int input) {
    int first = alpha(input);
    {
        struct Beta *client = 0;
        int nested = client->value;
    }
    return alpha(first) + client->value;
}
