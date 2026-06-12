package models.ssrf

import models.common.VulnChild

// 子类继承测试：通过VulnChild继承VulnParent形成SSRF调用链
// 测试场景：外部参数 → VulnController → SubClassTest → VulnChild → VulnParent → URL.openConnection
class SubClassTest {

  private val child = new VulnChild()

  // 【调用链入口】外部参数传入，经过子类到达父类SSRF漏洞点
  def testSsrf(input: String): String = {
    // 【调用链】SubClassTest.testSsrf → VulnChild.ssrfViaChild → VulnParent.ssrfWithParam → URL.openConnection
    child.ssrfViaChild(input)
  }

  // 【调用链入口】硬编码场景测试
  def testSsrfHardcoded(): String = {
    // 【调用链】SubClassTest.testSsrfHardcoded → VulnChild → VulnParent.ssrfHardcoded → URL.openConnection
    child.ssrfHardcoded()
  }
}
