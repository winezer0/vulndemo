package com.vulnweb.ssrf;

import com.vulnweb.common.VulnChild;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SSRF漏洞 - 纯子类继承调用测试文件
 * 通过 VulnChild 子类调用父类SSRF漏洞方法
 */
@RestController
public class SubClassTest {
    private VulnChild child = new VulnChild();

    /**
     * 测试SSRF：硬编码URL
     */
    @GetMapping("/ssrf/sub/hardcoded")
    public String testHardcoded() {
        String result = child.executeSsrf("http://example.com");
        return "子类继承硬编码SSRF结果: " + result;
    }

    /**
     * 测试SSRF：外部参数
     */
    @GetMapping("/ssrf/sub/param")
    public String testParam(@RequestParam String url) {
        // 调用链：请求参数 -> SubClassTest -> VulnChild -> VulnParent.ssrf
        String result = child.executeSsrf(url);
        return "子类继承带参SSRF结果: " + result;
    }
}