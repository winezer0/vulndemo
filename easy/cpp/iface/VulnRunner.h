#ifndef VULN_RUNNER_H
#define VULN_RUNNER_H

#include <string>

namespace iface {
    class VulnRunner {
    public:
        virtual void run(std::string cmd) = 0;
    };
}

#endif
