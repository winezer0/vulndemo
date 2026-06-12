package com.vulnweb.cmd_exec;

import com.vulnweb.common.VulnChild;
import com.vulnweb.api.VulnInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 命令执行漏洞 - 接口实现类调用测试文件
 * 实现 VulnInterface 接口，同时继承 VulnChild 类
 * 演示接口 + 类继承的组合调用
 */
@RestController
public class InterfaceImpl extends VulnChild implements VulnInterface {
    /**
     * 测试命令执行：硬编码命令
     * 通过接口方法调用，内部实现继承自 VulnChild
     */
    @GetMapping("/cmd/impl/hardcoded")
    public String testHardcoded() {
        // 调用接口方法，实际执行继承自 VulnChild 的漏洞方法
        String result = this.executeCmd("echo interfaceImplHardcoded");
        return "接口实现类硬编码命令执行结果: " + result;
    }

    /**
     * 测试命令执行：外部参数
     * 接收前端参数，通过接口调用，形成调用链：请求参数 -> InterfaceImpl -> VulnChild -> VulnParent
     */
    @GetMapping("/cmd/impl/param")
    public String testParam(@RequestParam String cmd) {
        // 调用接口方法，内部执行链：InterfaceImpl.executeCmd -> VulnChild.executeCmd -> VulnParent.cmdExec
        String result = this.executeCmd(cmd);
        return "接口实现类带参命令执行结果: " + result;
    }

    // 实现接口方法，委托给父类 VulnChild 的实现
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