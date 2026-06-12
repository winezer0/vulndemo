package com.vulnweb.ssrf

import com.vulnweb.common.VulnChild

// SSRF子类继承测试类
class SubClassTest {

    // 测试SSRF漏洞（外部参数）【漏洞点】
    fun testSsrf(input: String): String {
        val child = VulnChild()
        return child.fetchUrl(input)
    }

    // 测试SSRF漏洞（硬编码）【漏洞点】
    fun testSsrfHardcoded(): String {
        val child = VulnChild()
        return child.fetchUrlHardcoded()
    }
}
