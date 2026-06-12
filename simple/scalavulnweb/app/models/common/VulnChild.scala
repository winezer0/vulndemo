package models.common

// 独立子类：继承VulnParent，形成子类→父类漏洞调用链
class VulnChild extends VulnParent {

  // 子类特有方法：封装命令执行，内部调用父类漏洞方法
  def cmdExecViaChild(input: String): String = {
    // 【调用链】VulnChild.cmdExecViaChild → VulnParent.cmdExecWithParam → 命令执行漏洞点
    super.cmdExecWithParam(input)
  }

  // 子类特有方法：封装代码执行，内部调用父类漏洞方法
  def codeExecViaChild(input: String): String = {
    // 【调用链】VulnChild.codeExecViaChild → VulnParent.codeExecWithParam → 代码执行漏洞点
    super.codeExecWithParam(input)
  }

  // 子类特有方法：封装SSRF，内部调用父类漏洞方法
  def ssrfViaChild(input: String): String = {
    // 【调用链】VulnChild.ssrfViaChild → VulnParent.ssrfWithParam → SSRF漏洞点
    super.ssrfWithParam(input)
  }
}
