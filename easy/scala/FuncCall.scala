import common.VulnChild
import cmd_exec.InterfaceImpl
import iface.VulnRunner

object FuncCall {
  def runByChild(cmd: String): Unit = {
    val child = new VulnChild()
    child.exec(cmd)
  }

  def runByInterface(cmd: String): Unit = {
    val runner: VulnRunner = new InterfaceImpl()
    runner.run(cmd)
  }
}
