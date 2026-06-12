package com.vulnweb.code_exec;

import com.vulnweb.common.VulnChild;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码执行漏洞 - 纯子类继承调用测试文件
 * 通过 VulnChild 子类调用父类代码执行漏洞方法
 */
@RestController
public class SubClassTest {
    private VulnChild child = new VulnChild();

    /**
     * 测试代码执行：硬编码脚本
     */
    @GetMapping("/code/sub/hardcoded")
    public String testHardcoded() {
        String result = child.executeCode("1 + 2");
        return "子类继承硬编码代码执行结果: " + result;
    }

    /**
     * 测试代码执行：外部参数
     */
    @GetMapping("/code/sub/param")
    public String testParam(@RequestParam String script) {
        // 调用链：请求参数 -> SubClassTest -> VulnChild -> VulnParent.codeExec
        String result = child.executeCode(script);
        return "子类继承带参代码执行结果: " + result;
    }
}