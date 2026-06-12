package com.vulnweb;

import com.vulnweb.common.VulnParent;
import com.vulnweb.common.VulnChild;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跨文件函数调用测试类
 * 包含硬编码漏洞和外部参数调用，演示跨文件调用链
 */
@RestController
public class FuncCall {
    // 创建父类和子类实例，用于调用漏洞方法
    private VulnParent parent = new VulnParent();
    private VulnChild child = new VulnChild();

    /**
     * 测试跨文件调用：硬编码命令执行
     * 调用 VulnParent 的命令执行方法，无外部参数
     */
    @GetMapping("/func/cmd")
    public String testCmd() {
        // 调用父类漏洞方法，硬编码命令
        String result = parent.cmdExec("echo funcCallHardcoded");
        return "跨文件命令执行结果: " + result;
    }

    /**
     * 测试跨文件调用：外部参数命令执行
     * 接收前端参数，传递给子类，再调用父类漏洞方法
     */
    @GetMapping("/func/cmd/param")
    public String testCmdParam(@RequestParam String cmd) {
        // 子类调用父类漏洞方法，形成调用链：请求参数 -> FuncCall -> VulnChild -> VulnParent
        String result = child.executeCmd(cmd);
        return "跨文件带参命令执行结果: " + result;
    }

    /**
     * 测试跨文件调用：硬编码代码执行
     */
    @GetMapping("/func/code")
    public String testCode() {
        String result = parent.codeExec("1 + 1");
        return "跨文件代码执行结果: " + result;
    }

    /**
     * 测试跨文件调用：外部参数代码执行
     */
    @GetMapping("/func/code/param")
    public String testCodeParam(@RequestParam String script) {
        String result = child.executeCode(script);
        return "跨文件带参代码执行结果: " + result;
    }

    /**
     * 测试跨文件调用：硬编码SSRF
     */
    @GetMapping("/func/ssrf")
    public String testSsrf() {
        String result = parent.ssrf("http://example.com");
        return "跨文件SSRF结果: " + result;
    }

    /**
     * 测试跨文件调用：外部参数SSRF
     */
    @GetMapping("/func/ssrf/param")
    public String testSsrfParam(@RequestParam String url) {
        String result = child.executeSsrf(url);
        return "跨文件带参SSRF结果: " + result;
    }
}