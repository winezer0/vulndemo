package common

/**
 * 公共父类 - 包含各种漏洞方法
 * 用于测试类继承调用链
 */
class VulnParent {
    
    /**
     * 命令执行漏洞 - 硬编码场景
     * 漏洞点：直接使用 String.execute() 执行命令
     */
    void cmdExecHardcoded() {
        // 漏洞点：硬编码命令执行
        String cmd = "echo 'Vulnerability Test - Hardcoded Command'"
        cmd.execute()  // 漏洞：命令执行
    }
    
    /**
     * 命令执行漏洞 - 外部参数场景
     * 漏洞点：用户输入直接拼接命令执行
     * @param userInput 用户输入的命令参数
     */
    void cmdExecWithParam(String userInput) {
        // 漏洞点：外部参数拼接命令执行
        String cmd = "echo " + userInput
        cmd.execute()  // 漏洞：命令执行
    }
    
    /**
     * 代码执行漏洞 - 硬编码场景
     * 漏洞点：直接使用 Eval.me() 执行代码
     */
    void codeExecHardcoded() {
        // 漏洞点：硬编码代码执行
        String code = "2 + 2"
        def result = Eval.me(code)  // 漏洞：代码执行
        println "Result: ${result}"
    }
    
    /**
     * 代码执行漏洞 - 外部参数场景
     * 漏洞点：用户输入直接作为代码执行
     * @param userInput 用户输入的代码字符串
     */
    void codeExecWithParam(String userInput) {
        // 漏洞点：外部参数代码执行
        def result = Eval.me(userInput)  // 漏洞：代码执行
        println "Result: ${result}"
    }
    
    /**
     * SSRF漏洞 - 硬编码场景
     * 漏洞点：直接访问硬编码URL
     */
    void ssrfHardcoded() {
        // 漏洞点：硬编码URL访问
        URL url = new URL("http://internal-service.local/admin")
        HttpURLConnection conn = url.openConnection()  // 漏洞：SSRF
        conn.requestMethod = "GET"
        def response = conn.inputStream.text
        println "Response: ${response}"
    }
    
    /**
     * SSRF漏洞 - 外部参数场景
     * 漏洞点：用户输入URL直接访问
     * @param targetUrl 用户输入的目标URL
     */
    void ssrfWithParam(String targetUrl) {
        // 漏洞点：外部参数URL访问
        URL url = new URL(targetUrl)
        HttpURLConnection conn = url.openConnection()  // 漏洞：SSRF
        conn.requestMethod = "GET"
        def response = conn.inputStream.text
        println "Response: ${response}"
    }
}
