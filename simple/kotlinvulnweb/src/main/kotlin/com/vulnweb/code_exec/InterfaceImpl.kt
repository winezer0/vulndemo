package com.vulnweb.code_exec

import com.vulnweb.`interface`.IVulnInterface
import com.vulnweb.common.VulnChild

// 代码执行接口实现类
class InterfaceImpl : IVulnInterface {

    // 实现接口方法：测试命令执行漏洞（硬编码）【漏洞点】
    override fun testCmdExec(input: String): String {
        // 调用父类命令执行方法
        val child = VulnChild()
        return child.executeCommand(input)
    }

    // 实现接口方法：测试代码执行漏洞（外部参数）【漏洞点】
    override fun testCodeExec(input: String): String {
        val child = VulnChild()
        return child.executeCode(input)
    }

    // 实现接口方法：测试SSRF漏洞（硬编码）【漏洞点】
    override fun testSsrf(input: String): String {
        val child = VulnChild()
        return child.fetchUrlHardcoded()
    }

    // 额外方法：代码执行漏洞（硬编码）
    fun testCodeExecHardcoded(): String {
        val child = VulnChild()
        return child.executeCodeHardcoded()
    }
}
