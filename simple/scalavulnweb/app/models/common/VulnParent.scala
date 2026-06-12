package models.common

// 父类：包含命令执行、代码执行、SSRF漏洞方法
// 所有漏洞点通过子类继承、特质混入形成多层调用链
class VulnParent {

  // 【漏洞点】命令执行 - 硬编码场景（无外部输入）
  def cmdExecHardcoded(): String = {
    val cmd = "echo vuln_hardcoded_cmd"
    val runtime = Runtime.getRuntime
    // 【漏洞】直接执行硬编码命令，无任何过滤
    val process = runtime.exec(cmd)
    val result = scala.io.Source.fromInputStream(process.getInputStream).mkString
    process.waitFor()
    result
  }

  // 【漏洞点】命令执行 - 外部可控参数（通过子类/特质混入传递）
  def cmdExecWithParam(input: String): String = {
    val runtime = Runtime.getRuntime
    // 【漏洞】直接拼接外部参数执行命令，命令注入风险
    val process = runtime.exec(Array("sh", "-c", input))
    val result = scala.io.Source.fromInputStream(process.getInputStream).mkString
    process.waitFor()
    result
  }

  // 【漏洞点】代码执行 - 硬编码场景
  def codeExecHardcoded(): String = {
    import javax.script.{ScriptEngine, ScriptEngineManager}
    val manager = new ScriptEngineManager()
    val engine = manager.getEngineByName("js")
    // 【漏洞】直接执行硬编码脚本代码
    val code = "1 + 2 * 3"
    val result = engine.eval(code).toString
    result
  }

  // 【漏洞点】代码执行 - 外部可控参数
  def codeExecWithParam(input: String): String = {
    import javax.script.{ScriptEngine, ScriptEngineManager}
    val manager = new ScriptEngineManager()
    val engine = manager.getEngineByName("js")
    // 【漏洞】直接执行外部传入的脚本代码，代码注入风险
    val result = engine.eval(input).toString
    result
  }

  // 【漏洞点】SSRF - 硬编码场景
  def ssrfHardcoded(): String = {
    // 【漏洞】直接请求硬编码的内网地址
    val url = "http://127.0.0.1:8080/internal"
    val connection = new java.net.URL(url).openConnection()
    connection.setConnectTimeout(3000)
    val stream = connection.getInputStream
    val result = scala.io.Source.fromInputStream(stream).mkString
    stream.close()
    result
  }

  // 【漏洞点】SSRF - 外部可控参数
  def ssrfWithParam(input: String): String = {
    // 【漏洞】直接请求外部传入的URL，SSRF风险
    val connection = new java.net.URL(input).openConnection()
    connection.setConnectTimeout(3000)
    val stream = connection.getInputStream
    val result = scala.io.Source.fromInputStream(stream).mkString
    stream.close()
    result
  }
}
