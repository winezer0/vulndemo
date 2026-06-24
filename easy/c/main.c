#include "func_call.h"

int main(int argc, char* argv[]) {
    const char* cmd = argc > 1 ? argv[1] : "calc";
    // run_by_child(cmd);
    run_by_interface(cmd);
    return 0;
}
