#include "interface/vuln_interface.h"
#include <iostream>
#include <string>

// 代码执行漏洞 - 接口实现类调用测试文件
// 测试场景：通过纯虚类接口 IVulnInterface 的实现类 CodeExecInterfaceImpl 调用漏洞方法

void code_exec_interface_impl_test(const std::string& code) {
    std::cout << "=== 代码执行漏洞 - 接口实现类调用测试 ===" << std::endl;

    IVulnInterface* impl = new CodeExecInterfaceImpl();

    // 场景1：外部可控数据源
    // 调用链：IVulnInterface* -> CodeExecInterfaceImpl::executeCodeViaInterface -> VulnParent::executeCode -> popen（漏洞点）
    std::string result1 = impl->executeCodeViaInterface(code);
    std::cout << "[外部参数] 接口调用结果: " << result1 << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_code = "print('hardcoded_interface_code_exec')";
    std::string result2 = impl->executeCodeViaInterface(hardcoded_code);
    std::cout << "[硬编码] 接口调用结果: " << result2 << std::endl;

    delete impl;
}
