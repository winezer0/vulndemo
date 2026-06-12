package controllers

import javax.inject._
import play.api.mvc._
import models.cmd_exec.{SubClassTest => CmdSubClass}
import models.code_exec.{SubClassTest => CodeSubClass}
import models.ssrf.{SubClassTest => SsrfSubClass}
import models.cmd_exec.{TraitImpl => CmdTrait}
import models.code_exec.{TraitImpl => CodeTrait}
import models.ssrf.{TraitImpl => SsrfTrait}

// 主控制器：路由所有漏洞测试场景
// 外部参数通过 request.queryString 接收，传递给子类/特质混入类
@Singleton
class VulnController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // ==================== 命令执行测试 ====================

  // 【入口】命令执行 - 子类继承调用（外部可控参数）
  // 调用链：VulnController → CmdSubClass.testCmdExec → VulnChild → VulnParent → Runtime.exec
  def cmdExec(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val input = request.queryString.get("cmd").map(_.head).getOrElse("echo default")
    val subTest = new CmdSubClass()
    val result = subTest.testCmdExec(input)
    Ok(result)
  }

  // ==================== 代码执行测试 ====================

  // 【入口】代码执行 - 子类继承调用（外部可控参数）
  // 调用链：VulnController → CodeSubClass.testCodeExec → VulnChild → VulnParent → ScriptEngine.eval
  def codeExec(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val input = request.queryString.get("code").map(_.head).getOrElse("1+1")
    val subTest = new CodeSubClass()
    val result = subTest.testCodeExec(input)
    Ok(result)
  }

  // ==================== SSRF测试 ====================

  // 【入口】SSRF - 子类继承调用（外部可控参数）
  // 调用链：VulnController → SsrfSubClass.testSsrf → VulnChild → VulnParent → URL.openConnection
  def ssrfExec(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val input = request.queryString.get("url").map(_.head).getOrElse("http://127.0.0.1:8080")
    val subTest = new SsrfSubClass()
    val result = subTest.testSsrf(input)
    Ok(result)
  }

  // ==================== 硬编码场景测试 ====================

  // 【入口】命令执行 - 硬编码场景
  // 调用链：VulnController → CmdSubClass.testCmdExecHardcoded → VulnChild → VulnParent.cmdExecHardcoded → Runtime.exec
  def cmdExecHardcoded(): Action[AnyContent] = Action {
    val subTest = new CmdSubClass()
    val result = subTest.testCmdExecHardcoded()
    Ok(result)
  }

  // 【入口】代码执行 - 硬编码场景
  // 调用链：VulnController → CodeSubClass.testCodeExecHardcoded → VulnChild → VulnParent.codeExecHardcoded → ScriptEngine.eval
  def codeExecHardcoded(): Action[AnyContent] = Action {
    val subTest = new CodeSubClass()
    val result = subTest.testCodeExecHardcoded()
    Ok(result)
  }

  // 【入口】SSRF - 硬编码场景
  // 调用链：VulnController → SsrfSubClass.testSsrfHardcoded → VulnChild → VulnParent.ssrfHardcoded → URL.openConnection
  def ssrfExecHardcoded(): Action[AnyContent] = Action {
    val subTest = new SsrfSubClass()
    val result = subTest.testSsrfHardcoded()
    Ok(result)
  }
}
