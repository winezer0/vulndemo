package models.cmd_exec

import models.traits.VulnTrait
import models.common.VulnParent

// 特质实现类：混入VulnTrait特质，同时继承VulnParent父类
// 测试场景：外部参数 → VulnController → TraitImpl → VulnParent → 漏洞点
class TraitImpl extends VulnParent with VulnTrait {

  // 【特质实现】命令执行：外部可控参数
  def traitCmdExec(input: String): String = {
    // 【调用链】TraitImpl.traitCmdExec → VulnParent.cmdExecWithParam → Runtime.exec
    cmdExecWithParam(input)
  }

  // 【特质实现】代码执行：外部可控参数
  def traitCodeExec(input: String): String = {
    // 【调用链】TraitImpl.traitCodeExec → VulnParent.codeExecWithParam → ScriptEngine.eval
    codeExecWithParam(input)
  }

  // 【特质实现】SSRF：外部可控参数
  def traitSsrfExec(input: String): String = {
    // 【调用链】TraitImpl.traitSsrfExec → VulnParent.ssrfWithParam → URL.openConnection
    ssrfWithParam(input)
  }

  // 硬编码命令执行场景
  def traitCmdExecHardcoded(): String = {
    // 【调用链】TraitImpl.traitCmdExecHardcoded → VulnParent.cmdExecHardcoded → Runtime.exec
    cmdExecHardcoded()
  }
}
