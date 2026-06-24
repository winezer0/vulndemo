import '../common/vuln_child.dart';
import '../iface/vuln_runner.dart';

class InterfaceImpl extends VulnChild implements VulnRunner {
  @override
  void run(String cmd) {
    exec(cmd);
  }
}
