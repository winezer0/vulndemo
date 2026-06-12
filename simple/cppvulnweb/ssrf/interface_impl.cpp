#include "interface/vuln_interface.h"
#include <iostream>
#include <string>

// SSRF漏洞 - 接口实现类调用测试文件
// 测试场景：通过纯虚类接口 IVulnInterface 的实现类 SsrfInterfaceImpl 调用漏洞方法

void ssrf_interface_impl_test(const std::string& url) {
    std::cout << "=== SSRF漏洞 - 接口实现类调用测试 ===" << std::endl;

    IVulnInterface* impl = new SsrfInterfaceImpl();

    // 场景1：外部可控数据源
    // 调用链：IVulnInterface* -> SsrfInterfaceImpl::fetchUrlViaInterface -> VulnParent::fetchUrl -> curl_easy_perform（漏洞点）
    std::string result1 = impl->fetchUrlViaInterface(url);
    std::cout << "[外部参数] 接口SSRF结果: " << result1.substr(0, 200) << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_url = "http://example.com/hardcoded_interface_ssrf";
    std::string result2 = impl->fetchUrlViaInterface(hardcoded_url);
    std::cout << "[硬编码] 接口SSRF结果: " << result2.substr(0, 200) << std::endl;

    delete impl;
}
