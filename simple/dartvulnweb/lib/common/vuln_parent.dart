import 'dart:io';
import 'package:http/http.dart' as http;

/// 公共父类：包含三类漏洞的基础方法实现
/// 子类通过继承调用这些漏洞方法，形成继承调用链
class VulnParent {
  // ==================== 命令执行漏洞 ====================

  /// 父类命令执行方法（外部参数场景）
  /// 【漏洞点】外部输入未经过滤直接传入 Process.run
  String parentExecCommand(String userInput) {
    String cmd = userInput; // 【漏洞点】外部输入直接作为命令
    var result = Process.runSync(cmd, []);
    return result.stdout.toString();
  }

  /// 父类命令执行方法（硬编码场景）
  /// 【漏洞点】硬编码命令直接执行
  String parentExecHardcoded() {
    String cmd = 'whoami'; // 【漏洞点】硬编码系统命令
    var result = Process.runSync(cmd, []);
    return result.stdout.toString();
  }

  /// 父类带参数拼接的命令执行方法
  /// 【漏洞点】外部输入拼接到命令参数中
  String parentExecWithArg(String arg) {
    String cmd = 'ping'; // 【漏洞点】外部输入作为命令参数拼接
    var result = Process.runSync(cmd, [arg]);
    return result.stdout.toString();
  }

  // ==================== 代码执行漏洞 ====================

  /// 父类代码执行方法（外部参数场景）
  /// 【漏洞点】外部输入被构造为可执行代码片段后模拟执行
  String parentExecCode(String userInput) {
    String codeSnippet = 'eval("$userInput")'; // 【漏洞点】外部输入嵌入代码执行表达式
    return 'Executing code: $codeSnippet'; // 模拟代码执行（实际项目中可能调用 eval）
  }

  /// 父类代码执行方法（硬编码场景）
  /// 【漏洞点】硬编码代码片段被直接执行
  String parentExecCodeHardcoded() {
    String codeSnippet = 'eval("import(\'os\').system(\'ls\')")'; // 【漏洞点】硬编码恶意代码
    return 'Executing code: $codeSnippet';
  }

  /// 父类动态函数调用方法
  /// 【漏洞点】外部输入决定执行哪个函数
  String parentDynamicInvoke(String funcName) {
    return 'Dynamically invoking: $funcName()'; // 【漏洞点】外部输入作为函数名动态调用
  }

  // ==================== SSRF漏洞 ====================

  /// 父类SSRF方法（外部参数场景）
  /// 【漏洞点】外部输入直接构造请求URL
  Future<String> parentFetchUrl(String userInput) async {
    String targetUrl = 'http://127.0.0.1:8080/proxy?target=' + userInput; // 【漏洞点】外部输入拼接到URL
    try {
      var response = await http.get(Uri.parse(targetUrl));
      return 'Fetching URL: $targetUrl - Status: ${response.statusCode}';
    } catch (e) {
      return 'Fetching URL: $targetUrl - Error: $e';
    }
  }

  /// 父类SSRF方法（硬编码场景）
  /// 【漏洞点】硬编码内网地址
  Future<String> parentFetchHardcoded() async {
    String targetUrl = 'http://192.168.1.100:3306/admin'; // 【漏洞点】硬编码内网地址
    try {
      var response = await http.get(Uri.parse(targetUrl));
      return 'Fetching URL: $targetUrl - Status: ${response.statusCode}';
    } catch (e) {
      return 'Fetching URL: $targetUrl - Error: $e';
    }
  }

  /// 父类SSRF方法：通过Uri.parse解析用户输入
  /// 【漏洞点】用户输入直接用于构造Uri对象
  String parentParseUri(String userInput) {
    Uri uri = Uri.parse(userInput); // 【漏洞点】外部输入直接解析为URI
    return 'Parsed host: ${uri.host}, path: ${uri.path}';
  }
}
