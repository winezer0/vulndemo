package cmd_exec

import common.VulnChild
import iface.VulnRunner

class InterfaceImpl extends VulnChild with VulnRunner {
  def run(cmd: String): Unit = {
    exec(cmd)
  }
}
