import '../common/vuln_child.dart';
import '../func_call.dart';

/// 代码执行漏洞 - 纯子类继承调用测试
/// 调用链：外部参数 → CodeSubClassTest → VulnChild → VulnParent → eval表达式
class CodeSubClassTest {
  final VulnChild _child = VulnChild();

  /// 测试1：子类继承父类的代码执行（外部参数场景）
  /// 【漏洞点】外部输入最终到达eval表达式
  String testInheritedCodeExec(String userInput) {
    return _child.parentExecCode(userInput); // 继承调用链：child → parent → eval
  }

  /// 测试2：子类继承父类的硬编码代码执行
  /// 【漏洞点】硬编码恶意代码片段
  String testInheritedHardcoded() {
    return _child.parentExecCodeHardcoded(); // 继承调用链：child → parent → 硬编码eval
  }

  /// 测试3：子类特有方法通过跨文件函数构建代码
  /// 调用链：外部参数 → childBuildCode → crossFileBuildCodeSnippet → 代码拼接
  String testChildBuildCode(String input) {
    return _child.childBuildCode(input); // 子类特有 → 跨文件函数
  }

  /// 测试4：子类继承父类的动态函数调用（外部参数场景）
  /// 【漏洞点】外部输入作为函数名动态调用
  String testInheritedDynamicInvoke(String funcName) {
    return _child.parentDynamicInvoke(funcName); // 继承调用链：child → parent → 动态调用
  }

  /// 测试5：硬编码 + 子类包装 + 跨文件函数
  /// 调用链：硬编码 → childBuildCode → crossFileBuildCodeSnippet
  String testChildHardcodedBuild() {
    return _child.childBuildCode('import os; os.system("whoami")'); // 硬编码恶意代码经过子类+跨文件
  }
}
