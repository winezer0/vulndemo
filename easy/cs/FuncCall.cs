using common;
using iface;
using cmd_exec;

public class FuncCall {
    public static void RunByChild(string cmd) {
        VulnChild child = new VulnChild();
        child.Exec(cmd);
    }

    public static void RunByInterface(string cmd) {
        IVulnRunner runner = new InterfaceImpl();
        runner.Run(cmd);
    }
}
