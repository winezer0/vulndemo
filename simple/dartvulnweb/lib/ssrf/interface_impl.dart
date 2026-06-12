import '../interface/vuln_interface.dart';
import '../common/vuln_parent.dart';
import '../func_call.dart';

/// SSRF漏洞 - 接口实现类测试
/// 实现 VulnInterface 接口，同时继承 VulnParent 形成组合调用链
class SsrfInterfaceImpl extends VulnParent implements VulnInterface {
  /// 接口方法实现：命令执行（委托给父类）
  @override
  String executeCommand(String input) {
    return parentExecCommand(input);
  }

  /// 接口方法实现：代码执行（委托给父类）
  @override
  String executeCode(String input) {
    return parentExecCode(input);
  }

  /// 接口方法实现：SSRF（外部参数场景）
  /// 调用链：外部参数 → fetchTarget → crossFileBuildTargetUrl → parentFetchUrl
  @override
  Future<String> fetchTarget(String input) async {
    String url = crossFileBuildTargetUrl(input); // 【漏洞点】跨文件函数构建目标URL
    return parentFetchUrl(url); // 调用父类漏洞方法
  }

  /// 接口实现类特有方法：多层URL构造
  /// 调用链：外部参数 → ssrfChainFetch → 多层URL拼接 → parentFetchUrl
  Future<String> ssrfChainFetch(String input) async {
    String base = 'http://internal-proxy:8888/forward'; // 【漏洞点】硬编码内网代理地址
    String fullUrl = '$base?url=$input'; // 【漏洞点】外部输入拼接到内网URL
    return parentFetchUrl(fullUrl); // 传递到父类执行
  }

  /// 接口实现类特有方法：硬编码SSRF
  /// 调用链：硬编码 → interfaceImplFetch → parentFetchHardcoded
  Future<String> interfaceImplFetch() async {
    return parentFetchHardcoded(); // 接口实现类调用父类硬编码漏洞方法
  }

  /// 接口实现类特有方法：通过Uri.parse解析外部输入
  /// 调用链：外部参数 → dynamicUriParse → Uri.parse
  String dynamicUriParse(String input) {
    Uri uri = Uri.parse(input); // 【漏洞点】外部输入直接解析为Uri
    Map<String, String> params = uri.queryParameters; // 提取查询参数
    return 'Host: ${uri.host}, Params: $params';
  }
}
