#include "vuln_child.h"
#include <cstdio>
#include <cstdlib>
#include <curl/curl.h>
#include <sstream>

// 回调函数
static size_t ChildWriteCallback(void* contents, size_t size, size_t nmemb, std::string* userp) {
    userp->append((char*)contents, size * nmemb);
    return size * nmemb;
}

// 子类方法实现：调用父类漏洞方法形成多层调用链
// 调用链：VulnChild -> VulnParent -> 漏洞点

// 命令执行漏洞 - 子类调用父类方法
std::string VulnChild::executeCommand(const std::string& cmd) {
    // 调用链：VulnChild -> VulnParent::executeCommand -> popen（漏洞点）
    return VulnParent::executeCommand(cmd);
}

// 代码执行漏洞 - 子类调用父类方法
std::string VulnChild::executeCode(const std::string& code) {
    // 调用链：VulnChild -> VulnParent::executeCode -> popen（漏洞点）
    return VulnParent::executeCode(code);
}

// SSRF漏洞 - 子类调用父类方法
std::string VulnChild::fetchUrl(const std::string& url) {
    // 调用链：VulnChild -> VulnParent::fetchUrl -> curl_easy_perform（漏洞点）
    return VulnParent::fetchUrl(url);
}
