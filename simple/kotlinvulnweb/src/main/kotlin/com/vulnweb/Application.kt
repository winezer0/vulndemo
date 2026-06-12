package com.vulnweb

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import kotlinx.html.*
import com.vulnweb.common.VulnChild
import com.vulnweb.cmd_exec.SubClassTest as CmdSubClassTest
import com.vulnweb.cmd_exec.InterfaceImpl as CmdInterfaceImpl
import com.vulnweb.code_exec.SubClassTest as CodeSubClassTest
import com.vulnweb.code_exec.InterfaceImpl as CodeInterfaceImpl
import com.vulnweb.ssrf.SubClassTest as SsrfSubClassTest
import com.vulnweb.ssrf.InterfaceImpl as SsrfInterfaceImpl

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title("Kotlin VulnWeb 测试入口")
                    }
                    body {
                        h1 { +"Kotlin/Ktor 漏洞测试用例" }
                        ul {
                            li { a(href = "/cmd/exec?input=ls") { +"命令执行 - 子类继承（外部参数）" } }
                            li { a(href = "/cmd/exec-hardcoded") { +"命令执行 - 子类继承（硬编码）" } }
                            li { a(href = "/cmd/interface?input=ls") { +"命令执行 - 接口实现（外部参数）" } }
                            li { a(href = "/cmd/interface-hardcoded") { +"命令执行 - 接口实现（硬编码）" } }
                            li { a(href = "/code/exec?input=2+3") { +"代码执行 - 子类继承（外部参数）" } }
                            li { a(href = "/code/exec-hardcoded") { +"代码执行 - 子类继承（硬编码）" } }
                            li { a(href = "/code/interface?input=2+3") { +"代码执行 - 接口实现（外部参数）" } }
                            li { a(href = "/code/interface-hardcoded") { +"代码执行 - 接口实现（硬编码）" } }
                            li { a(href = "/ssrf/exec?url=http://example.com") { +"SSRF - 子类继承（外部参数）" } }
                            li { a(href = "/ssrf/exec-hardcoded") { +"SSRF - 子类继承（硬编码）" } }
                            li { a(href = "/ssrf/interface?url=http://example.com") { +"SSRF - 接口实现（外部参数）" } }
                            li { a(href = "/ssrf/interface-hardcoded") { +"SSRF - 接口实现（硬编码）" } }
                            li { a(href = "/cross/cmd?input=ls") { +"跨文件函数调用 - 命令执行（外部参数）" } }
                            li { a(href = "/cross/cmd-hardcoded") { +"跨文件函数调用 - 命令执行（硬编码）" } }
                            li { a(href = "/cross/code?input=2+3") { +"跨文件函数调用 - 代码执行（外部参数）" } }
                            li { a(href = "/cross/code-hardcoded") { +"跨文件函数调用 - 代码执行（硬编码）" } }
                            li { a(href = "/ssrf/cross?url=http://example.com") { +"跨文件函数调用 - SSRF（外部参数）" } }
                            li { a(href = "/ssrf/cross-hardcoded") { +"跨文件函数调用 - SSRF（硬编码）" } }
                            li { a(href = "/cross/subclass/cmd?input=ls") { +"跨文件类调用 - 命令执行子类（外部参数）" } }
                            li { a(href = "/cross/subclass/code?input=2+3") { +"跨文件类调用 - 代码执行子类（外部参数）" } }
                            li { a(href = "/cross/subclass/ssrf?url=http://example.com") { +"跨文件类调用 - SSRF子类（外部参数）" } }
                            li { a(href = "/cross/interface/cmd?input=ls") { +"跨文件类调用 - 命令执行接口（外部参数）" } }
                            li { a(href = "/cross/interface/code?input=2+3") { +"跨文件类调用 - 代码执行接口（外部参数）" } }
                            li { a(href = "/cross/interface/ssrf?url=http://example.com") { +"跨文件类调用 - SSRF接口（外部参数）" } }
                        }
                    }
                }
            }

            // 命令执行 - 子类继承（外部参数）
            get("/cmd/exec") {
                val input = call.request.queryParameters["input"] ?: "echo default"
                val child = VulnChild()
                val result = child.executeCommand(input) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // 命令执行 - 子类继承（硬编码）
            get("/cmd/exec-hardcoded") {
                val child = VulnChild()
                val result = child.executeCommandHardcoded() // 【漏洞点】硬编码命令
                call.respondText(result)
            }

            // 命令执行 - 接口实现（外部参数）
            get("/cmd/interface") {
                val input = call.request.queryParameters["input"] ?: "echo default"
                val impl = CmdInterfaceImpl()
                val result = impl.testCmdExec(input) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // 命令执行 - 接口实现（硬编码）
            get("/cmd/interface-hardcoded") {
                val impl = CmdInterfaceImpl()
                val result = impl.testCmdExecHardcoded() // 【漏洞点】硬编码命令
                call.respondText(result)
            }

            // 代码执行 - 子类继承（外部参数）
            get("/code/exec") {
                val input = call.request.queryParameters["input"] ?: "2 + 3"
                val child = VulnChild()
                val result = child.executeCode(input) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // 代码执行 - 子类继承（硬编码）
            get("/code/exec-hardcoded") {
                val child = VulnChild()
                val result = child.executeCodeHardcoded() // 【漏洞点】硬编码代码
                call.respondText(result)
            }

            // 代码执行 - 接口实现（外部参数）
            get("/code/interface") {
                val input = call.request.queryParameters["input"] ?: "2 + 3"
                val impl = CodeInterfaceImpl()
                val result = impl.testCodeExec(input) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // 代码执行 - 接口实现（硬编码）
            get("/code/interface-hardcoded") {
                val impl = CodeInterfaceImpl()
                val result = impl.testCodeExecHardcoded() // 【漏洞点】硬编码代码
                call.respondText(result)
            }

            // SSRF - 子类继承（外部参数）
            get("/ssrf/exec") {
                val url = call.request.queryParameters["url"] ?: "http://example.com"
                val child = VulnChild()
                val result = child.fetchUrl(url) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // SSRF - 子类继承（硬编码）
            get("/ssrf/exec-hardcoded") {
                val child = VulnChild()
                val result = child.fetchUrlHardcoded() // 【漏洞点】硬编码URL
                call.respondText(result)
            }

            // SSRF - 接口实现（外部参数）
            get("/ssrf/interface") {
                val url = call.request.queryParameters["url"] ?: "http://example.com"
                val impl = SsrfInterfaceImpl()
                val result = impl.testSsrf(url) // 【漏洞点】外部参数直接传入
                call.respondText(result)
            }

            // SSRF - 接口实现（硬编码）
            get("/ssrf/interface-hardcoded") {
                val impl = SsrfInterfaceImpl()
                val result = impl.testSsrfHardcoded() // 【漏洞点】硬编码URL
                call.respondText(result)
            }

            // 跨文件函数调用 - 命令执行（外部参数）
            get("/cross/cmd") {
                val input = call.request.queryParameters["input"] ?: "echo default"
                val result = crossFileCmdExec(input) // 【漏洞点】跨文件调用，外部参数
                call.respondText(result)
            }

            // 跨文件函数调用 - 命令执行（硬编码）
            get("/cross/cmd-hardcoded") {
                val result = crossFileCmdExecHardcoded() // 【漏洞点】跨文件调用，硬编码
                call.respondText(result)
            }

            // 跨文件函数调用 - 代码执行（外部参数）
            get("/cross/code") {
                val input = call.request.queryParameters["input"] ?: "2 + 3"
                val result = crossFileCodeExec(input) // 【漏洞点】跨文件调用，外部参数
                call.respondText(result)
            }

            // 跨文件函数调用 - 代码执行（硬编码）
            get("/cross/code-hardcoded") {
                val result = crossFileCodeExecHardcoded() // 【漏洞点】跨文件调用，硬编码
                call.respondText(result)
            }

            // 跨文件函数调用 - SSRF（外部参数）
            get("/ssrf/cross") {
                val url = call.request.queryParameters["url"] ?: "http://example.com"
                val result = crossFileSsrf(url) // 【漏洞点】跨文件调用，外部参数
                call.respondText(result)
            }

            // 跨文件函数调用 - SSRF（硬编码）
            get("/ssrf/cross-hardcoded") {
                val result = crossFileSsrfHardcoded() // 【漏洞点】跨文件调用，硬编码
                call.respondText(result)
            }

            // 跨文件类调用 - 命令执行子类（外部参数）
            get("/cross/subclass/cmd") {
                val input = call.request.queryParameters["input"] ?: "echo default"
                val result = crossFileCmdSubClassTest(input) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }

            // 跨文件类调用 - 代码执行子类（外部参数）
            get("/cross/subclass/code") {
                val input = call.request.queryParameters["input"] ?: "2 + 3"
                val result = crossFileCodeSubClassTest(input) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }

            // 跨文件类调用 - SSRF子类（外部参数）
            get("/cross/subclass/ssrf") {
                val url = call.request.queryParameters["url"] ?: "http://example.com"
                val result = crossFileSsrfSubClassTest(url) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }

            // 跨文件类调用 - 命令执行接口（外部参数）
            get("/cross/interface/cmd") {
                val input = call.request.queryParameters["input"] ?: "echo default"
                val result = crossFileCmdInterfaceTest(input) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }

            // 跨文件类调用 - 代码执行接口（外部参数）
            get("/cross/interface/code") {
                val input = call.request.queryParameters["input"] ?: "2 + 3"
                val result = crossFileCodeInterfaceTest(input) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }

            // 跨文件类调用 - SSRF接口（外部参数）
            get("/cross/interface/ssrf") {
                val url = call.request.queryParameters["url"] ?: "http://example.com"
                val result = crossFileSsrfInterfaceTest(url) // 【漏洞点】跨文件类调用，外部参数
                call.respondText(result)
            }
        }
    }.start(wait = true)
}
