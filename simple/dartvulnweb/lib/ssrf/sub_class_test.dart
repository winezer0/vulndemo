import '../common/vuln_child.dart';
import '../func_call.dart';

/// SSRF漏洞 - 纯子类继承调用测试
/// 调用链：外部参数 → SsrfSubClassTest → VulnChild → VulnParent → URL拼接/请求
class SsrfSubClassTest {
  final VulnChild _child = VulnChild();

  /// 测试1：子类继承父类的SSRF方法（外部参数场景）
  /// 【漏洞点】外部输入最终到达URL构造和请求
  Future<String> testInheritedFetchUrl(String userInput) async {
    return _child.parentFetchUrl(userInput); // 继承调用链：child → parent → URL拼接
  }

  /// 测试2：子类继承父类的硬编码SSRF
  /// 【漏洞点】硬编码内网地址
  Future<String> testInheritedHardcoded() async {
    return _child.parentFetchHardcoded(); // 继承调用链：child → parent → 硬编码URL
  }

  /// 测试3：子类特有方法通过跨文件函数构建URL
  /// 调用链：外部参数 → childParseUri → Uri.parse
  String testChildParseUri(String input) {
    return _child.childParseUri(input); // 子类特有 → Uri.parse解析
  }

  /// 测试4：子类继承父类的Uri.parse方法（外部参数场景）
  /// 【漏洞点】外部输入直接解析为Uri对象
  String testInheritedParseUri(String input) {
    return _child.parentParseUri(input); // 继承调用链：child → parent → Uri.parse
  }

  /// 测试5：硬编码 + 子类包装 + 跨文件函数
  /// 调用链：硬编码 → childFetchUrl → crossFileBuildTargetUrl → parentFetchUrl
  Future<String> testChildHardcodedFetch() async {
    return _child.parentFetchUrl('192.168.1.50'); // 硬编码IP经过子类+跨文件函数+父类
  }
}
