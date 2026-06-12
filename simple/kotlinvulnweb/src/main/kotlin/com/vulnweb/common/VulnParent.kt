package com.vulnweb.common

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import javax.script.ScriptEngineManager

// 公共父类：包含三类漏洞方法
open class VulnParent {

    // 命令执行漏洞方法（外部参数）【漏洞点】
    open fun executeCommand(input: String): String {
        val process = Runtime.getRuntime().exec(input)
        val output = process.inputStream.bufferedReader().readText()
        return "命令执行结果: $output"
    }

    // 命令执行漏洞方法（硬编码）【漏洞点】
    open fun executeCommandHardcoded(): String {
        val command = "ls -la"  // 硬编码命令
        val process = Runtime.getRuntime().exec(command)
        val output = process.inputStream.bufferedReader().readText()
        return "硬编码命令执行结果: $output"
    }

    // 代码执行漏洞方法（外部参数）【漏洞点】
    open fun executeCode(input: String): String {
        val engine = ScriptEngineManager().getEngineByName("JavaScript")
        val result = engine.eval(input)
        return "代码执行结果: $result"
    }

    // 代码执行漏洞方法（硬编码）【漏洞点】
    open fun executeCodeHardcoded(): String {
        val code = "2 + 3"  // 硬编码代码
        val engine = ScriptEngineManager().getEngineByName("JavaScript")
        val result = engine.eval(code)
        return "硬编码代码执行结果: $result"
    }

    // SSRF漏洞方法（外部参数）【漏洞点】
    open fun fetchUrl(input: String): String {
        val client = HttpClient(CIO)
        return runBlocking {
            val response = client.get(input)
            val content = response.bodyAsText()
            client.close()
            "SSRF获取内容: $content"
        }
    }

    // SSRF漏洞方法（硬编码）【漏洞点】
    open fun fetchUrlHardcoded(): String {
        val hardcodedUrl = "http://example.com"
        val client = HttpClient(CIO)
        return runBlocking {
            val response = client.get(hardcodedUrl)
            val content = response.bodyAsText()
            client.close()
            "硬编码SSRF获取内容: $content"
        }
    }
}
