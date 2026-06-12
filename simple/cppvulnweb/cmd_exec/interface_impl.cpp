#include "interface/vuln_interface.h"
#include <iostream>
#include <string>

// 命令执行漏洞 - 接口实现类调用测试文件
// 测试场景：通过纯虚类接口 IVulnInterface 的实现类 CmdExecInterfaceImpl 调用漏洞方法

void cmd_exec_interface_impl_test(const std::string& cmd) {
    std::cout << "=== 命令执行漏洞 - 接口实现类调用测试 ===" << std::endl;

    // 创建接口实现类实例（使用基类指针实现多态）
    IVulnInterface* impl = new CmdExecInterfaceImpl();

    // 场景1：外部可控数据源
    // 调用链：IVulnInterface* -> CmdExecInterfaceImpl::executeCommandViaInterface -> VulnParent::executeCommand -> popen（漏洞点）
    std::string result1 = impl->executeCommandViaInterface(cmd);
    std::cout << "[外部参数] 接口调用结果: " << result1 << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_cmd = "echo hardcoded_interface_cmd_exec";
    std::string result2 = impl->executeCommandViaInterface(hardcoded_cmd);
    std::cout << "[硬编码] 接口调用结果: " << result2 << std::endl;

    delete impl;
}
