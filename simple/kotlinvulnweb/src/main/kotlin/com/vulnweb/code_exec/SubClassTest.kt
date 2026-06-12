package com.vulnweb.code_exec

import com.vulnweb.common.VulnChild

// 代码执行子类继承测试类
class SubClassTest {

    // 测试代码执行漏洞（外部参数）【漏洞点】
    fun testCodeExec(input: String): String {
        val child = VulnChild()
        return child.executeCode(input)
    }

    // 测试代码执行漏洞（硬编码）【漏洞点】
    fun testCodeExecHardcoded(): String {
        val child = VulnChild()
        return child.executeCodeHardcoded()
    }
}
