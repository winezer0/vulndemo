package com.vulnweb.`interface`

// 漏洞测试接口
interface IVulnInterface {
    fun testCmdExec(input: String): String
    fun testCodeExec(input: String): String
    fun testSsrf(input: String): String
}
