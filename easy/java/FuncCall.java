import common.VulnChild;
import iface.VulnRunner;
import cmd_exec.InterfaceImpl;

public class FuncCall {
    public static void runByChild(String cmd) throws Exception {
        VulnChild child = new VulnChild();
        child.exec(cmd);
    }

    public static void runByInterface(String cmd) throws Exception {
        VulnRunner runner = new InterfaceImpl();
        runner.run(cmd);
    }
}
