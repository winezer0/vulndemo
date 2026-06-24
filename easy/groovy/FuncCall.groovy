import common.VulnChild
import cmd_exec.InterfaceImpl
import iface.VulnRunner

class FuncCall {
    static void runByChild(String cmd) {
        VulnChild child = new VulnChild()
        child.exec(cmd)
    }

    static void runByInterface(String cmd) {
        VulnRunner runner = new InterfaceImpl()
        runner.run(cmd)
    }
}
