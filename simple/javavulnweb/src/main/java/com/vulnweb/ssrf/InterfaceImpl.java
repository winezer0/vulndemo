package com.vulnweb.ssrf;

import com.vulnweb.common.VulnChild;
import com.vulnweb.api.VulnInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SSRF漏洞 - 接口实现类调用测试文件
 * 实现 VulnInterface 接口，同时继承 VulnChild 类
 */
@RestController
public class InterfaceImpl extends VulnChild implements VulnInterface {
    /**
     * 测试SSRF：硬编码URL
     */
    @GetMapping("/ssrf/impl/hardcoded")
    public String testHardcoded() {
        String result = this.executeSsrf("http://example.com");
        return "接口实现类硬编码SSRF结果: " + result;
    }

    /**
     * 测试SSRF：外部参数
     */
    @GetMapping("/ssrf/impl/param")
    public String testParam(@RequestParam String url) {
        // 调用链：请求参数 -> InterfaceImpl -> VulnChild -> VulnParent.ssrf
        String result = this.executeSsrf(url);
        return "接口实现类带参SSRF结果: " + result;
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