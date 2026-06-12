import '../interface/vuln_interface.dart';
import '../common/vuln_parent.dart';
import '../func_call.dart';

/// 代码执行漏洞 - 接口实现类测试
/// 实现 VulnInterface 接口，同时继承 VulnParent 形成组合调用链
class CodeInterfaceImpl extends VulnParent implements VulnInterface {
  /// 接口方法实现：命令执行（委托给父类）
  @override
  String executeCommand(String input) {
    return parentExecCommand(input);
  }

  /// 接口方法实现：代码执行（外部参数场景）
  /// 调用链：外部参数 → executeCode → crossFileBuildCodeSnippet → parentExecCode
  @override
  String executeCode(String input) {
    String code = crossFileBuildCodeSnippet(input); // 【漏洞点】跨文件函数构建代码
    return parentExecCode(code); // 调用父类漏洞方法
  }

  /// 接口方法实现：SSRF（委托给父类）
  @override
  Future<String> fetchTarget(String input) {
    return parentFetchUrl(input);
  }

  /// 接口实现类特有方法：多层代码构建
  /// 调用链：外部参数 → codeChainExec → crossFile函数 → parentExecCode → eval
  String codeChainExec(String input) {
    String step1 = 'print("$input")'; // 【漏洞点】外部输入嵌入代码
    String step2 = 'eval("$step1")'; // 【漏洞点】二次嵌入eval
    String finalCode = crossFileBuildCodeSnippet(step2); // 通过跨文件函数最终构建
    return parentExecCode(finalCode); // 传递到父类执行
  }

  /// 接口实现类特有方法：硬编码代码执行
  /// 调用链：硬编码 → interfaceImplCodeExec → parentExecCodeHardcoded
  String interfaceImplCodeExec() {
    return parentExecCodeHardcoded(); // 接口实现类调用父类硬编码漏洞方法
  }

  /// 接口实现类特有方法：动态代码构造
  /// 调用链：外部参数 → dynamicCodeBuild → 多层字符串拼接
  String dynamicCodeBuild(String input) {
    String template = 'function() { return "$input"; }'; // 【漏洞点】外部输入嵌入函数模板
    String wrapped = 'eval(new Function("$template"))'; // 【漏洞点】包装为可执行代码
    return 'Dynamic code: $wrapped';
  }
}
