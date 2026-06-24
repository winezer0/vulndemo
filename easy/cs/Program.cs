class Program {
    static void Main(string[] args) {
        string cmd = args.Length > 0 ? args[0] : "calc";
        // FuncCall.RunByChild(cmd);
        FuncCall.RunByInterface(cmd);
    }
}
