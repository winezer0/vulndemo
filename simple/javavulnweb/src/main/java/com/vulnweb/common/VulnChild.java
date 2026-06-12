package com.vulnweb.common;

/**
 * 独立子类，继承 VulnParent
 * 封装调用方法，形成子类继承父类的调用链
 */
public class VulnChild extends VulnParent {
    /**
     * 执行命令，调用父类的命令执行漏洞方法
     * @param cmd 命令参数
     * @return 命令执行结果
     */
    public String executeCmd(String cmd) {
        // 子类调用父类漏洞方法，形成调用链
        return super.cmdExec(cmd);
    }

    /**
     * 执行代码，调用父类的代码执行漏洞方法
     * @param script 脚本参数
     * @return 脚本执行结果
     */
    public String executeCode(String script) {
        return super.codeExec(script);
    }

    /**
     * 执行SSRF，调用父类的SSRF漏洞方法
     * @param url URL参数
     * @return HTTP响应结果
     */
    public String executeSsrf(String url) {
        return super.ssrf(url);
    }
}