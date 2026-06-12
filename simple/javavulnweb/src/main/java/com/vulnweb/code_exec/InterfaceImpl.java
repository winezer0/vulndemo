package com.vulnweb.code_exec;

import com.vulnweb.common.VulnChild;
import com.vulnweb.api.VulnInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码执行漏洞 - 接口实现类调用测试文件
 * 实现 VulnInterface 接口，同时继承 VulnChild 类
 */
@RestController
public class InterfaceImpl extends VulnChild implements VulnInterface {
    /**
     * 测试代码执行：硬编码脚本
     */
    @GetMapping("/code/impl/hardcoded")
    public String testHardcoded() {
        String result = this.executeCode("2 * 3");
        return "接口实现类硬编码代码执行结果: " + result;
    }

    /**
     * 测试代码执行：外部参数
     */
    @GetMapping("/code/impl/param")
    public String testParam(@RequestParam String script) {
        // 调用链：请求参数 -> InterfaceImpl -> VulnChild -> VulnParent.codeExec
        String result = this.executeCode(script);
        return "接口实现类带参代码执行结果: " + result;
    }

    @Override
    public String executeCmd(String cmd) {
        return super.executeCmd(cmd);
    }

    @Override
    public String executeCode(String script) {
        return super.executeCode(script);
    }

    @Override
    public String executeSsrf(String url) {
        return super.executeSsrf(url);
    }
}