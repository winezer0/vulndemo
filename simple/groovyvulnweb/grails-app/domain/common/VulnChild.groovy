package common

/**
 * 独立子类 - 继承自 VulnParent
 * 用于测试子类继承调用链
 */
class VulnChild extends VulnParent {
    
    /**
     * 子类特有方法 - 命令执行
     * 调用父类漏洞方法
     */
    void childCmdExec() {
        // 调用父类硬编码命令执行
        cmdExecHardcoded()
    }
    
    /**
     * 子类特有方法 - 带参数命令执行
     * 调用父类带参数漏洞方法
     * @param userInput 外部参数
     */
    void childCmdExecWithParam(String userInput) {
        // 调用父类带参数命令执行
        cmdExecWithParam(userInput)
    }
    
    /**
     * 子类特有方法 - 代码执行
     * 调用父类漏洞方法
     */
    void childCodeExec() {
        // 调用父类硬编码代码执行
        codeExecHardcoded()
    }
    
    /**
     * 子类特有方法 - 带参数代码执行
     * 调用父类带参数漏洞方法
     * @param userInput 外部参数
     */
    void childCodeExecWithParam(String userInput) {
        // 调用父类带参数代码执行
        codeExecWithParam(userInput)
    }
    
    /**
     * 子类特有方法 - SSRF
     * 调用父类漏洞方法
     */
    void childSsrf() {
        // 调用父类硬编码SSRF
        ssrfHardcoded()
    }
    
    /**
     * 子类特有方法 - 带参数SSRF
     * 调用父类带参数漏洞方法
     * @param targetUrl 外部参数URL
     */
    void childSsrfWithParam(String targetUrl) {
        // 调用父类带参数SSRF
        ssrfWithParam(targetUrl)
    }
}
