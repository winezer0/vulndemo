import 'common/vuln_child.dart';
import 'cmd_exec/interface_impl.dart';
import 'iface/vuln_runner.dart';

class FuncCall {
  static void runByChild(String cmd) {
    VulnChild child = VulnChild();
    child.exec(cmd);
  }

  static void runByInterface(String cmd) {
    VulnRunner runner = InterfaceImpl();
    runner.run(cmd);
  }
}
