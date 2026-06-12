import 'dart:io';

/// 通用跨文件函数调用工具类
/// 提供被多个漏洞模块共用的辅助函数，形成跨文件调用链路

/// 跨文件函数：拼接字符串并执行系统命令
/// 【漏洞点】参数直接拼接进命令字符串，无任何过滤
String crossFileExecCommand(String input) {
  String cmd = 'echo ' + input; // 【漏洞点】外部输入直接拼接到系统命令
  var result = Process.runSync('cmd', ['/c', cmd]);
  return result.stdout.toString();
}

/// 跨文件函数：构造动态代码片段
/// 【漏洞点】接收外部字符串拼接为可执行代码
String crossFileBuildCodeSnippet(String input) {
  String code = 'void main() { print("$input"); }'; // 【漏洞点】外部输入嵌入代码字符串
  return code;
}

/// 跨文件函数：构造SSRF目标URL
/// 【漏洞点】外部输入直接拼接到请求URL
String crossFileBuildTargetUrl(String input) {
  String url = 'http://internal-service.local/api?target=' + input; // 【漏洞点】外部输入拼接到URL
  return url;
}

/// 跨文件函数：日志记录（安全函数，无漏洞）
void crossFileLog(String message) {
  print('[LOG] $message');
}
