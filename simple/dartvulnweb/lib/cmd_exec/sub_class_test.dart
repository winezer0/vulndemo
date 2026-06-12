import 'dart:io';
import '../common/vuln_child.dart';

/// 命令执行漏洞 - 纯子类继承调用测试
/// 调用链：外部参数 → CmdSubClassTest → VulnChild → VulnParent → Process.run
class CmdSubClassTest {
  final VulnChild _child = VulnChild();

  /// 测试1：子类继承父类的命令执行（外部参数场景）
  /// 【漏洞点】外部输入最终到达 Process.runSync
  String testInheritedExec(String userInput) {
    return _child.parentExecCommand(userInput); // 继承调用链：child → parent → Process
  }

  /// 测试2：子类继承父类的硬编码命令执行
  /// 【漏洞点】硬编码命令直接执行
  String testInheritedHardcoded() {
    return _child.parentExecHardcoded(); // 继承调用链：child → parent → Process(硬编码)
  }

  /// 测试3：子类特有方法通过跨文件函数执行命令
  /// 调用链：外部参数 → childExecWithParam → crossFileExecCommand → 命令拼接
  String testChildSpecialMethod(String param) {
    return _child.childExecWithParam(param); // 子类特有 → 跨文件函数
  }

  /// 测试4：子类继承父类带参数的命令执行（外部参数场景）
  /// 【漏洞点】外部输入作为ping命令的参数
  String testInheritedWithArg(String arg) {
    return _child.parentExecWithArg(arg); // 继承调用链：child → parent(arg) → Process
  }

  /// 测试5：硬编码 + 子类包装
  /// 调用链：硬编码输入 → childExecWithParam → crossFileExecCommand
  String testChildHardcoded() {
    return _child.childExecWithParam('127.0.0.1'); // 硬编码参数经过子类+跨文件函数
  }
}
