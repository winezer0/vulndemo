import 'vuln_parent.dart';
import '../func_call.dart';

/// 独立子类：继承 VulnParent，覆写并扩展漏洞方法
/// 通过继承关系形成：子类 → 父类漏洞方法的多层调用链
class VulnChild extends VulnParent {
  // ==================== 命令执行漏洞（子类扩展） ====================

  /// 子类覆写的命令执行方法
  /// 调用链：外部参数 → childExecCommand → parentExecCommand → Process.run
  /// 【漏洞点】子类覆写后仍传递未过滤的外部输入到父类
  @override
  String parentExecCommand(String userInput) {
    String wrapped = 'echo [CHILD] ' + userInput; // 【漏洞点】子类再次拼接外部输入
    return super.parentExecCommand(wrapped); // 传递到父类执行
  }

  /// 子类特有的带参数命令执行
  /// 调用链：外部参数 → childExecWithParam → crossFileExecCommand → 命令拼接
  /// 【漏洞点】子类通过跨文件函数形成更长调用链
  String childExecWithParam(String param) {
    String cmd = crossFileExecCommand(param); // 【漏洞点】通过跨文件函数构建命令
    return 'Child exec cmd: $cmd';
  }

  // ==================== 代码执行漏洞（子类扩展） ====================

  /// 子类覆写的代码执行方法
  /// 调用链：外部参数 → childExecCode → parentExecCode → eval表达式
  @override
  String parentExecCode(String userInput) {
    String snippet = 'import("exec").execute("$userInput")'; // 【漏洞点】子类构造恶意代码片段
    return super.parentExecCode(snippet);
  }

  /// 子类特有的动态代码构建
  /// 调用链：外部参数 → childBuildCode → crossFileBuildCodeSnippet
  String childBuildCode(String input) {
    String code = crossFileBuildCodeSnippet(input); // 【漏洞点】跨文件构建代码
    return 'Child built code: $code';
  }

  // ==================== SSRF漏洞（子类扩展） ====================

  /// 子类覆写的SSRF方法
  /// 调用链：外部参数 → childFetchUrl → parentFetchUrl → URL拼接
  @override
  Future<String> parentFetchUrl(String userInput) async {
    String url = crossFileBuildTargetUrl(userInput); // 【漏洞点】跨文件构建目标URL
    return super.parentFetchUrl(url);
  }

  /// 子类特有的Uri解析方法
  /// 调用链：外部参数 → childParseUri → Uri.parse
  String childParseUri(String input) {
    Uri uri = Uri.parse(input); // 【漏洞点】子类直接解析外部输入为Uri
    return 'Child parsed URI - host: ${uri.host}, scheme: ${uri.scheme}';
  }
}
