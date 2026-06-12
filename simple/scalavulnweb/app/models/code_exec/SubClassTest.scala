package models.code_exec

import models.common.VulnChild

// 子类继承测试：通过VulnChild继承VulnParent形成代码执行调用链
// 测试场景：外部参数 → VulnController → SubClassTest → VulnChild → VulnParent → ScriptEngine.eval
class SubClassTest {

  private val child = new VulnChild()

  // 【调用链入口】外部参数传入，经过子类到达父类代码执行漏洞点
  def testCodeExec(input: String): String = {
    // 【调用链】SubClassTest.testCodeExec → VulnChild.codeExecViaChild → VulnParent.codeExecWithParam → ScriptEngine.eval
    child.codeExecViaChild(input)
  }

  // 【调用链入口】硬编码场景测试
  def testCodeExecHardcoded(): String = {
    // 【调用链】SubClassTest.testCodeExecHardcoded → VulnChild → VulnParent.codeExecHardcoded → ScriptEngine.eval
    child.codeExecHardcoded()
  }
}
