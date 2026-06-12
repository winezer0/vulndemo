package com.vulnweb

import com.vulnweb.common.VulnParent
import com.vulnweb.common.VulnChild
import com.vulnweb.cmd_exec.SubClassTest as CmdSubClassTest
import com.vulnweb.cmd_exec.InterfaceImpl as CmdInterfaceImpl
import com.vulnweb.code_exec.SubClassTest as CodeSubClassTest
import com.vulnweb.code_exec.InterfaceImpl as CodeInterfaceImpl
import com.vulnweb.ssrf.SubClassTest as SsrfSubClassTest
import com.vulnweb.ssrf.InterfaceImpl as SsrfInterfaceImpl

// 跨文件函数调用：命令执行漏洞（外部参数）
fun crossFileCmdExec(input: String): String {
    val child = VulnChild()
    return child.executeCommand(input)
}

// 跨文件函数调用：命令执行漏洞（硬编码）
fun crossFileCmdExecHardcoded(): String {
    val child = VulnChild()
    return child.executeCommandHardcoded()
}

// 跨文件函数调用：代码执行漏洞（外部参数）
fun crossFileCodeExec(input: String): String {
    val child = VulnChild()
    return child.executeCode(input)
}

// 跨文件函数调用：代码执行漏洞（硬编码）
fun crossFileCodeExecHardcoded(): String {
    val child = VulnChild()
    return child.executeCodeHardcoded()
}

// 跨文件函数调用：SSRF漏洞（外部参数）
fun crossFileSsrf(input: String): String {
    val child = VulnChild()
    return child.fetchUrl(input)
}

// 跨文件函数调用：SSRF漏洞（硬编码）
fun crossFileSsrfHardcoded(): String {
    val child = VulnChild()
    return child.fetchUrlHardcoded()
}

// 跨文件类实例调用：命令执行（子类继承）
fun crossFileCmdSubClassTest(input: String): String {
    val test = CmdSubClassTest()
    return test.testCmdExec(input)
}

// 跨文件类实例调用：命令执行（接口实现）
fun crossFileCmdInterfaceTest(input: String): String {
    val impl = CmdInterfaceImpl()
    return impl.testCmdExec(input)
}

// 跨文件类实例调用：代码执行（子类继承）
fun crossFileCodeSubClassTest(input: String): String {
    val test = CodeSubClassTest()
    return test.testCodeExec(input)
}

// 跨文件类实例调用：代码执行（接口实现）
fun crossFileCodeInterfaceTest(input: String): String {
    val impl = CodeInterfaceImpl()
    return impl.testCodeExec(input)
}

// 跨文件类实例调用：SSRF（子类继承）
fun crossFileSsrfSubClassTest(input: String): String {
    val test = SsrfSubClassTest()
    return test.testSsrf(input)
}

// 跨文件类实例调用：SSRF（接口实现）
fun crossFileSsrfInterfaceTest(input: String): String {
    val impl = SsrfInterfaceImpl()
    return impl.testSsrf(input)
}
