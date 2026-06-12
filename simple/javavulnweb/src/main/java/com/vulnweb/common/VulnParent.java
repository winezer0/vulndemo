package com.vulnweb.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 公共父类，集中存放所有漏洞核心方法
 * 包含命令执行、代码执行、SSRF三类高危漏洞
 */
public class VulnParent {
    /**
     * 命令执行漏洞点
     * 使用 Runtime.getRuntime().exec() 执行外部命令
     * @param cmd 要执行的命令
     * @return 命令执行结果
     */
    public String cmdExec(String cmd) {
        try {
            // 漏洞点：直接执行用户传入的命令，未做任何过滤
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
            return output.toString();
        } catch (Exception e) {
            return "命令执行错误: " + e.getMessage();
        }
    }

    /**
     * 代码执行漏洞点
     * 使用 ScriptEngine 执行JavaScript代码
     * @param script 要执行的脚本
     * @return 脚本执行结果
     */
    public String codeExec(String script) {
        try {
            // 漏洞点：直接执行用户传入的脚本，未做任何过滤
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            Object result = engine.eval(script);
            return result.toString();
        } catch (Exception e) {
            return "代码执行错误: " + e.getMessage();
        }
    }

    /**
     * SSRF漏洞点
     * 使用 HttpURLConnection 发起HTTP请求
     * @param url 目标URL
     * @return HTTP响应内容
     */
    public String ssrf(String url) {
        try {
            // 漏洞点：直接请求用户传入的URL，未做任何过滤
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            return "SSRF错误: " + e.getMessage();
        }
    }
}