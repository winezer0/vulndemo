import 'dart:io';
import '../interface/vuln_interface.dart';
import '../common/vuln_parent.dart';
import '../func_call.dart';

/// 命令执行漏洞 - 接口实现类测试
/// 实现 AdvancedVulnInterface 接口，同时继承 VulnParent 形成组合调用链
class CmdInterfaceImpl extends VulnParent implements AdvancedVulnInterface {
  /// 接口方法实现：命令执行（外部参数场景）
  /// 调用链：外部参数 → executeCommand → parentExecCommand → Process.run
  @override
  String executeCommand(String input) {
    String cmd = crossFileExecCommand(input); // 【漏洞点】跨文件函数拼接命令
    return parentExecCommand(cmd); // 调用父类漏洞方法
  }

  /// 接口方法实现：代码执行（占位，委托给父类）
  @override
  String executeCode(String input) {
    return parentExecCode(input);
  }

  /// 接口方法实现：SSRF（占位，委托给父类）
  @override
  Future<String> fetchTarget(String input) {
    return parentFetchUrl(input);
  }

  /// 二级接口方法：带参数命令执行
  /// 调用链：外部参数 → executeCommandWithArgs → Process.runSync(带参数列表)
  @override
  String executeCommandWithArgs(String cmd, List<String> args) {
    String fullCmd = cmd + ' ' + args.join(' '); // 【漏洞点】命令和参数直接拼接
    var result = Process.runSync(fullCmd, []); // 【漏洞点】拼接后的命令直接执行
    return result.stdout.toString();
  }

  /// 二级接口方法：硬编码命令执行
  /// 调用链：硬编码 → executeCommandWithArgs → Process.runSync
  @override
  String executeCodeWithConfig(String code, Map<String, String> config) {
    String hardcodedCmd = 'cat /etc/passwd'; // 【漏洞点】硬编码敏感命令
    var result = Process.runSync(hardcodedCmd, []);
    return result.stdout.toString();
  }

  /// 二级接口方法：带认证的SSRF（硬编码场景）
  @override
  Future<String> fetchWithAuth(String url, String token) async {
    String target = 'http://admin.internal:9090?token=$token&target=$url'; // 【漏洞点】硬编码内网地址+外部token
    return 'Fetching: $target';
  }

  /// 接口实现类特有方法：通过父类执行硬编码命令
  /// 调用链：硬编码 → interfaceImplExec → parentExecHardcoded → Process
  String interfaceImplExec() {
    return parentExecHardcoded(); // 接口实现类调用父类硬编码漏洞方法
  }
}
