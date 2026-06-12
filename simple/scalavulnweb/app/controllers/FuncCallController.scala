package controllers

import javax.inject._
import play.api.mvc._
import utils.FuncCall
import models.cmd_exec.{TraitImpl => CmdTrait}
import models.code_exec.{TraitImpl => CodeTrait}
import models.ssrf.{TraitImpl => SsrfTrait}

// 跨文件调用控制器：测试跨文件函数调用、特质混入调用场景
// 外部参数通过 request.queryString 接收
@Singleton
class FuncCallController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // 【跨文件调用入口】通过FuncCall工具类调用各漏洞模块
  // 调用链：FuncCallController → FuncCall.crossCmdExec → models.cmd_exec.SubClassTest → VulnChild → VulnParent → Runtime.exec
  def funcCall(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val cmd = request.queryString.get("cmd").map(_.head).getOrElse("echo cross_file")
    val code = request.queryString.get("code").map(_.head).getOrElse("1+2")
    val url = request.queryString.get("url").map(_.head).getOrElse("http://127.0.0.1:8080")

    val cmdResult = FuncCall.crossCmdExec(cmd)
    val codeResult = FuncCall.crossCodeExec(code)
    val ssrfResult = FuncCall.crossSsrfExec(url)

    Ok(s"Cmd: $cmdResult | Code: $codeResult | Ssrf: $ssrfResult")
  }

  // 【特质混入调用入口】通过TraitImpl测试特质+类继承组合调用
  // 调用链：FuncCallController → TraitImpl.traitCmdExec → VulnParent.cmdExecWithParam → Runtime.exec
  def traitCall(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val cmd = request.queryString.get("cmd").map(_.head).getOrElse("echo trait_test")
    val code = request.queryString.get("code").map(_.head).getOrElse("3*3")
    val url = request.queryString.get("url").map(_.head).getOrElse("http://127.0.0.1:8080")

    val cmdTrait = new CmdTrait()
    val codeTrait = new CodeTrait()
    val ssrfTrait = new SsrfTrait()

    val cmdResult = cmdTrait.traitCmdExec(cmd)
    val codeResult = codeTrait.traitCodeExec(code)
    val ssrfResult = ssrfTrait.traitSsrfExec(url)

    Ok(s"Trait Cmd: $cmdResult | Trait Code: $codeResult | Trait Ssrf: $ssrfResult")
  }
}
