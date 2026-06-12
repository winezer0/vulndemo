package com.vulnweb.cmd_exec

import com.vulnweb.common.VulnChild

// 命令执行子类继承测试类
class SubClassTest {

    // 测试命令执行漏洞（外部参数）【漏洞点】
    fun testCmdExec(input: String): String {
        val child = VulnChild()
        return child.executeCommand(input)
    }

    // 测试命令执行漏洞（硬编码）【漏洞点】
    fun testCmdExecHardcoded(): String {
        val child = VulnChild()
        return child.executeCommandHardcoded()
    }
}
