package utils

/**
 * 跨文件函数调用工具类
 * 包含各种漏洞方法的独立函数
 */
class FuncCall {
    
    /**
     * 命令执行函数 - 硬编码场景
     * 漏洞点：直接使用 String.execute() 执行命令
     */
    static void cmdExecFunction() {
        // 漏洞点：硬编码命令执行
        String cmd = "echo 'Vulnerability Test - FuncCall Command'"
        cmd.execute()  // 漏洞：命令执行
    }
    
    /**
     * 命令执行函数 - 外部参数场景
     * 漏洞点：用户输入直接拼接命令执行
     * @param userInput 用户输入的命令参数
     */
    static void cmdExecFunctionWithParam(String userInput) {
        // 漏洞点：外部参数拼接命令执行
        String cmd = "echo " + userInput
        cmd.execute()  // 漏洞：命令执行
    }
    
    /**
     * 代码执行函数 - 硬编码场景
     * 漏洞点：直接使用 Eval.me() 执行代码
     */
    static void codeExecFunction() {
        // 漏洞点：硬编码代码执行
        String code = "2 + 2"
        def result = Eval.me(code)  // 漏洞：代码执行
        println "FuncCall Result: ${result}"
    }
    
    /**
     * 代码执行函数 - 外部参数场景
     * 漏洞点：用户输入直接作为代码执行
     * @param userInput 用户输入的代码字符串
     */
    static void codeExecFunctionWithParam(String userInput) {
        // 漏洞点：外部参数代码执行
        def result = Eval.me(userInput)  // 漏洞：代码执行
        println "FuncCall Result: ${result}"
    }
    
    /**
     * SSRF函数 - 硬编码场景
     * 漏洞点：直接访问硬编码URL
     */
    static void ssrfFunction() {
        // 漏洞点：硬编码URL访问
        URL url = new URL("http://internal-service.local/admin")
        HttpURLConnection conn = url.openConnection()  // 漏洞：SSRF
        conn.requestMethod = "GET"
        def response = conn.inputStream.text
        println "FuncCall Response: ${response}"
    }
    
    /**
     * SSRF函数 - 外部参数场景
     * 漏洞点：用户输入URL直接访问
     * @param targetUrl 用户输入的目标URL
     */
    static void ssrfFunctionWithParam(String targetUrl) {
        // 漏洞点：外部参数URL访问
        URL url = new URL(targetUrl)
        HttpURLConnection conn = url.openConnection()  // 漏洞：SSRF
        conn.requestMethod = "GET"
        def response = conn.inputStream.text
        println "FuncCall Response: ${response}"
    }
}
