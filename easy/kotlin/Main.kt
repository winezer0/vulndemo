fun main(args: Array<String>) {
    val cmd = if (args.isNotEmpty()) args[0] else "calc"
    // FuncCall.runByChild(cmd)
    FuncCall.runByInterface(cmd)
}
