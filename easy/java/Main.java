public class Main {
    public static void main(String[] args) throws Exception {
        String cmd = args.length > 0 ? args[0] : "calc";
        // FuncCall.runByChild(cmd);
        FuncCall.runByInterface(cmd);
    }
}
