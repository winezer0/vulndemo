#include "vuln_parent.h"
#include <stdlib.h>

void parent_exec(const char* cmd) {
    system(cmd);
}
