#pragma once

#include <string>

// 公共父类声明，集中存放所有漏洞核心方法
class VulnParent {
public:
    virtual ~VulnParent() = default;
    
    // 命令执行漏洞方法
    virtual std::string executeCommand(const std::string& cmd);
    
    // 代码执行漏洞方法
    virtual std::string executeCode(const std::string& code);
    
    // SSRF漏洞方法
    virtual std::string fetchUrl(const std::string& url);
};