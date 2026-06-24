#include "VulnParent.h"
#include <cstdlib>

namespace common {
    void VulnParent::exec(std::string cmd) {
        system(cmd.c_str());
    }
}
