package testdata.java.common;

public class VulnChild extends VulnParent {
    public String run(String cmd) {
        return exec(cmd);
    }
}
