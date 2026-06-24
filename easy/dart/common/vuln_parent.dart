import 'dart:io';

class VulnParent {
  void exec(String cmd) {
    Process.runSync('cmd', ['/c', cmd]);
  }
}
