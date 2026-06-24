#ifndef INTERFACE_IMPL_H
#define INTERFACE_IMPL_H

#include "../common/vuln_child.h"
#include "../iface/vuln_runner.h"

typedef struct {
    VulnChild child;
    VulnRunner runner;
} InterfaceImpl;

void impl_run(void* self, const char* cmd);

#endif
