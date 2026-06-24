import common.VulnChild
import cmd_exec.InterfaceImpl
import iface.VulnRunner

object FuncCall {
    fun runByChild(cmd: String) {
        val child = VulnChild()
        child.exec(cmd)
    }

    fun runByInterface(cmd: String) {
        val runner: VulnRunner = InterfaceImpl()
        runner.run(cmd)
    }
}
