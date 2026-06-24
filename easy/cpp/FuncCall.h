#ifndef FUNC_CALL_H
#define FUNC_CALL_H

#include <string>

class FuncCall {
public:
    static void runByChild(std::string cmd);
    static void runByInterface(std::string cmd);
};

#endif
