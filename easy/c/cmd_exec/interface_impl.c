#include "interface_impl.h"

void impl_run(void* self, const char* cmd) {
    ((InterfaceImpl*)self)->child.parent.exec(cmd);
}
