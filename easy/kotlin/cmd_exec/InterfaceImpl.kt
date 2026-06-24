package cmd_exec

import common.VulnChild
import iface.VulnRunner

class InterfaceImpl : VulnChild(), VulnRunner {
    override fun run(cmd: String) {
        exec(cmd)
    }
}
