#ifndef INTERFACE_IMPL_H
#define INTERFACE_IMPL_H

#include "../common/VulnChild.h"
#include "../iface/VulnRunner.h"

namespace cmd_exec {
    class InterfaceImpl : public common::VulnChild, public iface::VulnRunner {
    public:
        void run(std::string cmd) override;
    };
}

#endif
