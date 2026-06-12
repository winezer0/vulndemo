package com.vulnweb.common

// 子类继承父类，复用漏洞方法
class VulnChild : VulnParent() {

    // 重写命令执行方法（可选择是否调用父类）
    override fun executeCommand(input: String): String {
        // 子类可以添加额外逻辑，然后调用父类漏洞方法
        val childLog = "子类日志: 准备执行命令"
        val result = super.executeCommand(input)
        return "$childLog\n$result"
    }

    // 重写代码执行方法
    override fun executeCode(input: String): String {
        val childLog = "子类日志: 准备执行代码"
        val result = super.executeCode(input)
        return "$childLog\n$result"
    }

    // 重写SSRF方法
    override fun fetchUrl(input: String): String {
        val childLog = "子类日志: 准备获取URL"
        val result = super.fetchUrl(input)
        return "$childLog\n$result"
    }
}
