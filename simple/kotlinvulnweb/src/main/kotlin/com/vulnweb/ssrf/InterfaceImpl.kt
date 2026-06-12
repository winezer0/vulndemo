package com.vulnweb.ssrf

import com.vulnweb.`interface`.IVulnInterface
import com.vulnweb.common.VulnChild

// SSRF接口实现类
class InterfaceImpl : IVulnInterface {

    // 实现接口方法：测试命令执行漏洞（硬编码）【漏洞点】
    override fun testCmdExec(input: String): String {
        val child = VulnChild()
        return child.executeCommand(input)
    }

    // 实现接口方法：测试代码执行漏洞（硬编码）【漏洞点】
    override fun testCodeExec(input: String): String {
        val child = VulnChild()
        return child.executeCode(input)
    }

    // 实现接口方法：测试SSRF漏洞（外部参数）【漏洞点】
    override fun testSsrf(input: String): String {
        val child = VulnChild()
        return child.fetchUrl(input)
    }

    // 额外方法：SSRF漏洞（硬编码）
    fun testSsrfHardcoded(): String {
        val child = VulnChild()
        return child.fetchUrlHardcoded()
    }
}
