<?php

// 公共父类 VulnParent，集中存放所有漏洞核心方法
class VulnParent
{
    // 命令执行漏洞核心方法，通过参数 $cmd 传入外部可控数据触发漏洞点
    public function execCommand($cmd)
    {
        // 漏洞点：直接将参数传给 system() 执行系统命令
        echo "<div class='result-block'>";
        echo "<h3>[Command Execution Test]</h3>";
        echo "<p>Executed Command: " . htmlspecialchars($cmd) . "</p>";
        echo "<pre>";
        system($cmd);
        echo "</pre>";
        echo "</div>";
    }

    // 命令执行漏洞核心方法（硬编码版本，无外部数据源）
    public function execCommandHardcoded()
    {
        // 漏洞点：内置固定命令参数直接执行
        $fixedCmd = 'whoami && echo "hardcoded-command-exec"';
        echo "<div class='result-block'>";
        echo "<h3>[Command Execution Hardcoded Test]</h3>";
        echo "<p>Executed Command: " . htmlspecialchars($fixedCmd) . "</p>";
        echo "<pre>";
        system($fixedCmd);
        echo "</pre>";
        echo "</div>";
    }

    // 代码执行漏洞核心方法，通过参数 $code 传入外部可控数据触发漏洞点
    public function execCode($code)
    {
        // 漏洞点：直接将参数传给 eval() 执行 PHP 代码
        echo "<div class='result-block'>";
        echo "<h3>[Code Execution Test]</h3>";
        echo "<p>Executed Code: " . htmlspecialchars($code) . "</p>";
        echo "<pre>";
        eval($code);
        echo "</pre>";
        echo "</div>";
    }

    // 代码执行漏洞核心方法（硬编码版本，无外部数据源）
    public function execCodeHardcoded()
    {
        // 漏洞点：内置固定 PHP 代码字符串直接执行
        $fixedCode = 'echo phpversion() . " | hardcoded-code-exec";';
        echo "<div class='result-block'>";
        echo "<h3>[Code Execution Hardcoded Test]</h3>";
        echo "<p>Executed Code: " . htmlspecialchars($fixedCode) . "</p>";
        echo "<pre>";
        eval($fixedCode);
        echo "</pre>";
        echo "</div>";
    }

    // SSRF 漏洞核心方法，通过参数 $url 传入外部可控数据触发漏洞点
    public function fetchUrl($url)
    {
        // 漏洞点：直接将参数传给 file_get_contents() 发起 HTTP 请求
        echo "<div class='result-block'>";
        echo "<h3>[SSRF Test]</h3>";
        echo "<p>Requested URL: " . htmlspecialchars($url) . "</p>";
        echo "<pre>";
        $content = @file_get_contents($url);
        if ($content === false) {
            echo "Failed to fetch: " . htmlspecialchars($url);
        } else {
            echo htmlspecialchars(substr($content, 0, 500));
        }
        echo "</pre>";
        echo "</div>";
    }

    // SSRF 漏洞核心方法（硬编码版本，无外部数据源）
    public function fetchUrlHardcoded()
    {
        // 漏洞点：内置固定 URL 直接发起请求
        $fixedUrl = 'http://127.0.0.1/';
        echo "<div class='result-block'>";
        echo "<h3>[SSRF Hardcoded Test]</h3>";
        echo "<p>Requested URL: " . htmlspecialchars($fixedUrl) . "</p>";
        echo "<pre>";
        $content = @file_get_contents($fixedUrl);
        if ($content === false) {
            echo "Failed to fetch: " . htmlspecialchars($fixedUrl);
        } else {
            echo htmlspecialchars(substr($content, 0, 500));
        }
        echo "</pre>";
        echo "</div>";
    }
}
