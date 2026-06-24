#include "FuncCall.h"
#include "common/VulnChild.h"
#include "cmd_exec/InterfaceImpl.h"

void FuncCall::runByChild(std::string cmd) {
    common::VulnChild child;
    child.exec(cmd);
}

void FuncCall::runByInterface(std::string cmd) {
    cmd_exec::InterfaceImpl impl;
    iface::VulnRunner* runner = &impl;
    runner->run(cmd);
}
