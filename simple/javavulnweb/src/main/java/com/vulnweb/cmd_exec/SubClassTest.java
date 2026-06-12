package com.vulnweb.cmd_exec;

import com.vulnweb.common.VulnChild;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 命令执行漏洞 - 纯子类继承调用测试文件
 * 通过 VulnChild 子类调用父类漏洞方法
 */
@RestController
public class SubClassTest {
    // 创建子类实例
    private VulnChild child = new VulnChild();

    /**
     * 测试命令执行：硬编码命令
     * 调用子类方法，子类调用父类漏洞方法
     */
    @GetMapping("/cmd/sub/hardcoded")
    public String testHardcoded() {
        String result = child.executeCmd("echo subClassHardcoded");
        return "子类继承硬编码命令执行结果: " + result;
    }

    /**
     * 测试命令执行：外部参数
     * 接收前端参数，通过子类调用父类漏洞方法
     */
    @GetMapping("/cmd/sub/param")
    public String testParam(@RequestParam String cmd) {
        // 调用链：请求参数 -> SubClassTest -> VulnChild -> VulnParent.cmdExec
        String result = child.executeCmd(cmd);
        return "子类继承带参命令执行结果: " + result;
    }
}