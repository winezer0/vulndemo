import 'func_call.dart';

void main(List<String> args) {
  String cmd = args.isNotEmpty ? args[0] : "calc";
  // FuncCall.runByChild(cmd);
  FuncCall.runByInterface(cmd);
}
