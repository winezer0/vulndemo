package testdata.java.common;

public class VulnParent {
    public String exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception ignored) {
        }
        return cmd;
    }
}
