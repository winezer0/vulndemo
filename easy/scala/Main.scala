object Main {
  def main(args: Array[String]): Unit = {
    val cmd = if (args.length > 0) args(0) else "calc"
    // FuncCall.runByChild(cmd)
    FuncCall.runByInterface(cmd)
  }
}
