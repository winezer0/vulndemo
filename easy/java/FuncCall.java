package testdata.java;

import testdata.java.cmd_exec.InterfaceImpl;
import testdata.java.common.VulnChild;
import testdata.java.iface.VulnRunner;

public class FuncCall {
    public String runByChild(String cmd) {
        return new VulnChild().run(cmd);
    }

    public String runByInterface(String cmd) {
        VulnRunner runner = new InterfaceImpl();
        return runner.run(cmd);
    }
}
