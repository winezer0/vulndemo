package com.vulnweb.api;

/**
 * 独立接口定义文件
 * 定义漏洞执行接口，用于接口实现类调用
 */
public interface VulnInterface {
    /**
     * 执行命令
     * @param cmd 命令参数
     * @return 执行结果
     */
    String executeCmd(String cmd);

    /**
     * 执行代码
     * @param script 脚本参数
     * @return 执行结果
     */
    String executeCode(String script);

    /**
     * 执行SSRF
     * @param url URL参数
     * @return 执行结果
     */
    String executeSsrf(String url);
}