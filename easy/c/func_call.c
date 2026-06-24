#include "func_call.h"
#include "common/vuln_child.h"
#include "cmd_exec/interface_impl.h"

void run_by_child(const char* cmd) {
    VulnChild child = { {parent_exec} };
    child.parent.exec(cmd);
}

void run_by_interface(const char* cmd) {
    VulnChild child = { {parent_exec} };
    InterfaceImpl impl = { child, {impl_run} };
    impl.runner.run(&impl, cmd);
}
