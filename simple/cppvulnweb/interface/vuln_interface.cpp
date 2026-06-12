#include "vuln_interface.h"
#include "common/vuln_parent.h"
#include <cstdio>
#include <cstdlib>
#include <curl/curl.h>
#include <sstream>

// 回调函数
static size_t IfaceWriteCallback(void* contents, size_t size, size_t nmemb, std::string* userp) {
    userp->append((char*)contents, size * nmemb);
    return size * nmemb;
}

// ==================== CmdExecInterfaceImpl 实现 ====================
// 命令执行接口实现类：通过接口+类继承组合调用

std::string CmdExecInterfaceImpl::executeCommandViaInterface(const std::string& cmd) {
    // 调用链：接口方法 -> VulnParent漏洞方法 -> popen（漏洞点）
    VulnParent parent;
    return parent.executeCommand(cmd);
}

std::string CmdExecInterfaceImpl::executeCodeViaInterface(const std::string& code) {
    VulnParent parent;
    return parent.executeCode(code);
}

std::string CmdExecInterfaceImpl::fetchUrlViaInterface(const std::string& url) {
    VulnParent parent;
    return parent.fetchUrl(url);
}

// ==================== CodeExecInterfaceImpl 实现 ====================
// 代码执行接口实现类：通过接口+类继承组合调用

std::string CodeExecInterfaceImpl::executeCommandViaInterface(const std::string& cmd) {
    VulnParent parent;
    return parent.executeCommand(cmd);
}

std::string CodeExecInterfaceImpl::executeCodeViaInterface(const std::string& code) {
    // 调用链：接口方法 -> VulnParent漏洞方法 -> popen模拟代码执行（漏洞点）
    VulnParent parent;
    return parent.executeCode(code);
}

std::string CodeExecInterfaceImpl::fetchUrlViaInterface(const std::string& url) {
    VulnParent parent;
    return parent.fetchUrl(url);
}

// ==================== SsrfInterfaceImpl 实现 ====================
// SSRF接口实现类：通过接口+类继承组合调用

std::string SsrfInterfaceImpl::executeCommandViaInterface(const std::string& cmd) {
    VulnParent parent;
    return parent.executeCommand(cmd);
}

std::string SsrfInterfaceImpl::executeCodeViaInterface(const std::string& code) {
    VulnParent parent;
    return parent.executeCode(code);
}

std::string SsrfInterfaceImpl::fetchUrlViaInterface(const std::string& url) {
    // 调用链：接口方法 -> VulnParent漏洞方法 -> curl_easy_perform（漏洞点）
    VulnParent parent;
    return parent.fetchUrl(url);
}
