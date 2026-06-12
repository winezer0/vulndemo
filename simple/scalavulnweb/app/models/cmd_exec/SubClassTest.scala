package models.cmd_exec

import models.common.VulnChild

// 子类继承测试：通过VulnChild继承VulnParent形成调用链
// 测试场景：外部参数 → VulnController → SubClassTest → VulnChild → VulnParent → 漏洞点
class SubClassTest {

  private val child = new VulnChild()

  // 【调用链入口】外部参数传入，经过子类到达父类漏洞点
  def testCmdExec(input: String): String = {
    // 【调用链】SubClassTest.testCmdExec → VulnChild.cmdExecViaChild → VulnParent.cmdExecWithParam → Runtime.exec
    child.cmdExecViaChild(input)
  }

  // 【调用链入口】硬编码场景测试
  def testCmdExecHardcoded(): String = {
    // 【调用链】SubClassTest.testCmdExecHardcoded → VulnChild → VulnParent.cmdExecHardcoded → Runtime.exec
    child.cmdExecHardcoded()
  }
}
