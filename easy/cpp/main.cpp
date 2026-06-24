#include "FuncCall.h"

int main(int argc, char* argv[]) {
    std::string cmd = argc > 1 ? argv[1] : "calc";
    // FuncCall::runByChild(cmd);
    FuncCall::runByInterface(cmd);
    return 0;
}
