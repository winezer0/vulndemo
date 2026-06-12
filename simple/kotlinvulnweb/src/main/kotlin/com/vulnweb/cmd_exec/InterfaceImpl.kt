package com.vulnweb.cmd_exec

import com.vulnweb.`interface`.IVulnInterface
import com.vulnweb.common.VulnChild

// 命令执行接口实现类
class InterfaceImpl : IVulnInterface {

    // 实现接口方法：测试命令执行漏洞（外部参数）【漏洞点】
    override fun testCmdExec(input: String): String {
        val child = VulnChild()
        return child.executeCommand(input)
    }

    // 实现接口方法：测试命令执行漏洞（硬编码）【漏洞点】
    override fun testCodeExec(input: String): String {
        // 此方法在命令执行目录，但接口要求实现testCodeExec，这里调用命令执行漏洞
        val child = VulnChild()
        return child.executeCommand(input)
    }

    // 实现接口方法：测试SSRF漏洞（硬编码）【漏洞点】
    override fun testSsrf(input: String): String {
        // 调用父类SSRF方法
        val child = VulnChild()
        return child.fetchUrlHardcoded()
    }

    // 额外方法：命令执行漏洞（硬编码）
    fun testCmdExecHardcoded(): String {
        val child = VulnChild()
        return child.executeCommandHardcoded()
    }
}
