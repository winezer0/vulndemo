import 'dart:io';
import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as shelf_io;
import 'package:shelf_static/shelf_static.dart';

import '../lib/cmd_exec/sub_class_test.dart';
import '../lib/cmd_exec/interface_impl.dart';
import '../lib/code_exec/sub_class_test.dart';
import '../lib/code_exec/interface_impl.dart';
import '../lib/ssrf/sub_class_test.dart';
import '../lib/ssrf/interface_impl.dart';
import '../lib/func_call.dart';

/// 项目总入口：Shelf服务器启动，注册所有测试路由
void main() async {
  // 实例化各漏洞测试类
  final cmdSubTest = CmdSubClassTest();
  final cmdInterface = CmdInterfaceImpl();
  final codeSubTest = CodeSubClassTest();
  final codeInterface = CodeInterfaceImpl();
  final ssrfSubTest = SsrfSubClassTest();
  final ssrfInterface = SsrfInterfaceImpl();

  // ==================== 命令执行漏洞路由 ====================

  // 子类继承：外部参数命令执行
  final cmdSubInheritedHandler = (Request request) async {
    String? param = request.url.queryParameters['cmd'];
    param ??= 'hello';
    String result = cmdSubTest.testInheritedExec(param);
    return Response.ok('CMD Sub Inherited: $result');
  };

  // 子类继承：硬编码命令执行
  final cmdSubHardcodedHandler = (Request request) async {
    String result = cmdSubTest.testInheritedHardcoded();
    return Response.ok('CMD Sub Hardcoded: $result');
  };

  // 子类特有：跨文件函数命令执行
  final cmdSubSpecialHandler = (Request request) async {
    String? param = request.url.queryParameters['param'];
    param ??= 'test';
    String result = cmdSubTest.testChildSpecialMethod(param);
    return Response.ok('CMD Sub Special: $result');
  };

  // 接口实现：外部参数命令执行
  final cmdInterfaceExecHandler = (Request request) async {
    String? param = request.url.queryParameters['cmd'];
    param ??= 'hello';
    String result = cmdInterface.executeCommand(param);
    return Response.ok('CMD Interface Exec: $result');
  };

  // 接口实现：带参数命令执行
  final cmdInterfaceArgsHandler = (Request request) async {
    String? cmd = request.url.queryParameters['cmd'];
    String? args = request.url.queryParameters['args'];
    cmd ??= 'whoami';
    args ??= '';
    List<String> argList = args.split(',').where((e) => e.isNotEmpty).toList();
    String result = cmdInterface.executeCommandWithArgs(cmd, argList);
    return Response.ok('CMD Interface Args: $result');
  };

  // 接口实现：硬编码命令执行
  final cmdInterfaceHardcodedHandler = (Request request) async {
    String result = cmdInterface.interfaceImplExec();
    return Response.ok('CMD Interface Hardcoded: $result');
  };

  // ==================== 代码执行漏洞路由 ====================

  // 子类继承：外部参数代码执行
  final codeSubInheritedHandler = (Request request) async {
    String? param = request.url.queryParameters['code'];
    param ??= 'hello';
    String result = codeSubTest.testInheritedCodeExec(param);
    return Response.ok('Code Sub Inherited: $result');
  };

  // 子类继承：硬编码代码执行
  final codeSubHardcodedHandler = (Request request) async {
    String result = codeSubTest.testInheritedHardcoded();
    return Response.ok('Code Sub Hardcoded: $result');
  };

  // 子类特有：跨文件函数代码构建
  final codeSubBuildHandler = (Request request) async {
    String? param = request.url.queryParameters['code'];
    param ??= 'test';
    String result = codeSubTest.testChildBuildCode(param);
    return Response.ok('Code Sub Build: $result');
  };

  // 子类继承：动态函数调用
  final codeSubDynamicHandler = (Request request) async {
    String? param = request.url.queryParameters['func'];
    param ??= 'defaultFunc';
    String result = codeSubTest.testInheritedDynamicInvoke(param);
    return Response.ok('Code Sub Dynamic: $result');
  };

  // 接口实现：外部参数代码执行
  final codeInterfaceExecHandler = (Request request) async {
    String? param = request.url.queryParameters['code'];
    param ??= 'hello';
    String result = codeInterface.executeCode(param);
    return Response.ok('Code Interface Exec: $result');
  };

  // 接口实现：多层代码构建
  final codeInterfaceChainHandler = (Request request) async {
    String? param = request.url.queryParameters['code'];
    param ??= 'test';
    String result = codeInterface.codeChainExec(param);
    return Response.ok('Code Interface Chain: $result');
  };

  // 接口实现：硬编码代码执行
  final codeInterfaceHardcodedHandler = (Request request) async {
    String result = codeInterface.interfaceImplCodeExec();
    return Response.ok('Code Interface Hardcoded: $result');
  };

  // 接口实现：动态代码构造
  final codeInterfaceDynamicHandler = (Request request) async {
    String? param = request.url.queryParameters['code'];
    param ??= 'hello';
    String result = codeInterface.dynamicCodeBuild(param);
    return Response.ok('Code Interface Dynamic: $result');
  };

  // ==================== SSRF漏洞路由 ====================

  // 子类继承：外部参数SSRF
  final ssrfSubFetchHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com';
    String result = await ssrfSubTest.testInheritedFetchUrl(param);
    return Response.ok('SSRF Sub Fetch: $result');
  };

  // 子类继承：硬编码SSRF
  final ssrfSubHardcodedHandler = (Request request) async {
    String result = await ssrfSubTest.testInheritedHardcoded();
    return Response.ok('SSRF Sub Hardcoded: $result');
  };

  // 子类继承：Uri.parse解析
  final ssrfSubParseHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com/path';
    String result = ssrfSubTest.testInheritedParseUri(param);
    return Response.ok('SSRF Sub ParseUri: $result');
  };

  // 子类特有：childParseUri
  final ssrfSubChildParseHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com/path';
    String result = ssrfSubTest.testChildParseUri(param);
    return Response.ok('SSRF Sub ChildParse: $result');
  };

  // 接口实现：外部参数SSRF
  final ssrfInterfaceFetchHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com';
    String result = await ssrfInterface.fetchTarget(param);
    return Response.ok('SSRF Interface Fetch: $result');
  };

  // 接口实现：多层URL构造
  final ssrfInterfaceChainHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com';
    String result = await ssrfInterface.ssrfChainFetch(param);
    return Response.ok('SSRF Interface Chain: $result');
  };

  // 接口实现：硬编码SSRF
  final ssrfInterfaceHardcodedHandler = (Request request) async {
    String result = await ssrfInterface.interfaceImplFetch();
    return Response.ok('SSRF Interface Hardcoded: $result');
  };

  // 接口实现：动态Uri解析
  final ssrfInterfaceDynamicHandler = (Request request) async {
    String? param = request.url.queryParameters['url'];
    param ??= 'http://example.com/path?key=value';
    String result = ssrfInterface.dynamicUriParse(param);
    return Response.ok('SSRF Interface DynamicUri: $result');
  };

  // ==================== 跨文件函数路由 ====================

  final crossFileCmdHandler = (Request request) async {
    String? param = request.url.queryParameters['input'];
    param ??= 'test';
    String result = crossFileExecCommand(param);
    return Response.ok('CrossFile Cmd: $result');
  };

  final crossFileCodeHandler = (Request request) async {
    String? param = request.url.queryParameters['input'];
    param ??= 'test';
    String result = crossFileBuildCodeSnippet(param);
    return Response.ok('CrossFile Code: $result');
  };

  final crossFileUrlHandler = (Request request) async {
    String? param = request.url.queryParameters['input'];
    param ??= 'test';
    String result = crossFileBuildTargetUrl(param);
    return Response.ok('CrossFile Url: $result');
  };

  // ==================== 注册路由 ====================

  final router = Router()
    // 静态文件（index.html）
    ..mount('/', createStaticHandler('web'))
    // 命令执行 - 子类继承
    ..get('/cmd/sub/inherited', cmdSubInheritedHandler)
    ..get('/cmd/sub/hardcoded', cmdSubHardcodedHandler)
    ..get('/cmd/sub/special', cmdSubSpecialHandler)
    // 命令执行 - 接口实现
    ..get('/cmd/iface/exec', cmdInterfaceExecHandler)
    ..get('/cmd/iface/args', cmdInterfaceArgsHandler)
    ..get('/cmd/iface/hardcoded', cmdInterfaceHardcodedHandler)
    // 代码执行 - 子类继承
    ..get('/code/sub/inherited', codeSubInheritedHandler)
    ..get('/code/sub/hardcoded', codeSubHardcodedHandler)
    ..get('/code/sub/build', codeSubBuildHandler)
    ..get('/code/sub/dynamic', codeSubDynamicHandler)
    // 代码执行 - 接口实现
    ..get('/code/iface/exec', codeInterfaceExecHandler)
    ..get('/code/iface/chain', codeInterfaceChainHandler)
    ..get('/code/iface/hardcoded', codeInterfaceHardcodedHandler)
    ..get('/code/iface/dynamic', codeInterfaceDynamicHandler)
    // SSRF - 子类继承
    ..get('/ssrf/sub/fetch', ssrfSubFetchHandler)
    ..get('/ssrf/sub/hardcoded', ssrfSubHardcodedHandler)
    ..get('/ssrf/sub/parse', ssrfSubParseHandler)
    ..get('/ssrf/sub/childparse', ssrfSubChildParseHandler)
    // SSRF - 接口实现
    ..get('/ssrf/iface/fetch', ssrfInterfaceFetchHandler)
    ..get('/ssrf/iface/chain', ssrfInterfaceChainHandler)
    ..get('/ssrf/iface/hardcoded', ssrfInterfaceHardcodedHandler)
    ..get('/ssrf/iface/dynamicuri', ssrfInterfaceDynamicHandler)
    // 跨文件函数
    ..get('/cross/cmd', crossFileCmdHandler)
    ..get('/cross/code', crossFileCodeHandler)
    ..get('/cross/url', crossFileUrlHandler);

  // 启动服务器
  final handler = const Pipeline().addMiddleware(logRequests()).addHandler(router.call);
  final server = await shelf_io.serve(handler, InternetAddress.anyIPv4, 8080);
  print('Server running on http://${server.address.host}:${server.port}');
  print('Test page: http://localhost:${server.port}/index.html');
}
