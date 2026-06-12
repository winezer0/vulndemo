package testdata.java.cmd_exec;

import testdata.java.common.VulnChild;
import testdata.java.iface.VulnRunner;

public class InterfaceImpl extends VulnChild implements VulnRunner {
    @Override
    public String run(String cmd) {
        return exec(cmd);
    }
}
