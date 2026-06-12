package utils

import models.common.VulnChild
import models.cmd_exec.{SubClassTest => CmdSub}
import models.code_exec.{SubClassTest => CodeSub}
import models.ssrf.{SubClassTest => SsrfSub}

// 跨文件函数调用工具类
// 测试场景：FuncCallController → FuncCall → 各漏洞模块子类 → 父类 → 漏洞点
object FuncCall {

  // 【跨文件调用】命令执行 - 通过子类调用
  def crossCmdExec(input: String): String = {
    // 【调用链】FuncCall.crossCmdExec → models.cmd_exec.SubClassTest.testCmdExec → VulnChild → VulnParent → Runtime.exec
    val test = new CmdSub()
    test.testCmdExec(input)
  }

  // 【跨文件调用】代码执行 - 通过子类调用
  def crossCodeExec(input: String): String = {
    // 【调用链】FuncCall.crossCodeExec → models.code_exec.SubClassTest.testCodeExec → VulnChild → VulnParent → ScriptEngine.eval
    val test = new CodeSub()
    test.testCodeExec(input)
  }

  // 【跨文件调用】SSRF - 通过子类调用
  def crossSsrfExec(input: String): String = {
    // 【调用链】FuncCall.crossSsrfExec → models.ssrf.SubClassTest.testSsrf → VulnChild → VulnParent → URL.openConnection
    val test = new SsrfSub()
    test.testSsrf(input)
  }
}
