package cmd_exec

import common.VulnChild
import iface.VulnRunner

class InterfaceImpl extends VulnChild implements VulnRunner {
    void run(String cmd) {
        exec(cmd)
    }
}
