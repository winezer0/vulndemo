package models.traits

// 特质定义：声明漏洞方法签名，由实现类混入
trait VulnTrait {

  // 特质声明：命令执行方法
  def traitCmdExec(input: String): String

  // 特质声明：代码执行方法
  def traitCodeExec(input: String): String

  // 特质声明：SSRF方法
  def traitSsrfExec(input: String): String
}
