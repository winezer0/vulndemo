/// 独立接口定义：声明三类漏洞的抽象方法
/// 各漏洞目录下的实现类必须实现此接口，形成接口调用链
abstract class VulnInterface {
  /// 命令执行接口方法
  /// 【漏洞点】实现类中将外部输入传递给Process.run
  String executeCommand(String input);

  /// 代码执行接口方法
  /// 【漏洞点】实现类中将外部输入构造为可执行代码
  String executeCode(String input);

  /// SSRF接口方法
  /// 【漏洞点】实现类中将外部输入用于构造请求URL
  Future<String> fetchTarget(String input);
}

/// 二级抽象接口：组合接口，增加高级操作定义
/// 实现类同时继承 VulnInterface 并实现此接口，形成多重调用链
abstract class AdvancedVulnInterface extends VulnInterface {
  /// 带参数的命令执行
  String executeCommandWithArgs(String cmd, List<String> args);

  /// 带配置的代码执行
  String executeCodeWithConfig(String code, Map<String, String> config);

  /// 带认证的SSRF请求
  Future<String> fetchWithAuth(String url, String token);
}
