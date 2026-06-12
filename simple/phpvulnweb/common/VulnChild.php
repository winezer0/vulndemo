<?php

require_once __DIR__ . '/VulnParent.php';

// 子类 VulnChild，继承 VulnParent，封装调用父类漏洞方法
class VulnChild extends VulnParent
{
    // 封装调用父类命令执行漏洞方法，接收外部可控参数 $cmd
    public function runCommand($cmd)
    {
        $this->execCommand($cmd);
    }

    // 封装调用父类命令执行漏洞方法（硬编码版本）
    public function runCommandFixed()
    {
        $this->execCommandHardcoded();
    }

    // 封装调用父类代码执行漏洞方法，接收外部可控参数 $code
    public function runCode($code)
    {
        $this->execCode($code);
    }

    // 封装调用父类代码执行漏洞方法（硬编码版本）
    public function runCodeFixed()
    {
        $this->execCodeHardcoded();
    }

    // 封装调用父类 SSRF 漏洞方法，接收外部可控参数 $url
    public function runFetch($url)
    {
        $this->fetchUrl($url);
    }

    // 封装调用父类 SSRF 漏洞方法（硬编码版本）
    public function runFetchFixed()
    {
        $this->fetchUrlHardcoded();
    }
}
