#include "common/vuln_child.h"
#include <iostream>
#include <string>

// SSRF漏洞 - 纯子类继承调用测试文件
// 测试场景：通过子类 VulnChild 继承父类 VulnParent 的SSRF漏洞方法

void ssrf_sub_class_test(const std::string& url) {
    std::cout << "=== SSRF漏洞 - 子类继承调用测试 ===" << std::endl;

    VulnChild child;

    // 场景1：外部可控数据源（通过请求参数传入）
    // 调用链：VulnChild -> VulnParent::fetchUrl -> curl_easy_perform（漏洞点）
    std::string result1 = child.fetchUrl(url);
    std::cout << "[外部参数] SSRF请求结果: " << result1.substr(0, 200) << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_url = "http://example.com/hardcoded_ssrf_test";
    std::string result2 = child.fetchUrl(hardcoded_url);
    std::cout << "[硬编码] SSRF请求结果: " << result2.substr(0, 200) << std::endl;
}
