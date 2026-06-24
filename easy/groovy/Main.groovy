class Main {
    static void main(String[] args) {
        String cmd = args.length > 0 ? args[0] : "calc"
        // FuncCall.runByChild(cmd)
        FuncCall.runByInterface(cmd)
    }
}
