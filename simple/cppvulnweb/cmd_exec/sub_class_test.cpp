#include "common/vuln_child.h"
#include <iostream>
#include <string>

// 命令执行漏洞 - 纯子类继承调用测试文件
// 测试场景：通过子类 VulnChild 继承父类 VulnParent 的漏洞方法

void cmd_exec_sub_class_test(const std::string& cmd) {
    std::cout << "=== 命令执行漏洞 - 子类继承调用测试 ===" << std::endl;

    // 创建子类实例
    VulnChild child;

    // 场景1：外部可控数据源（通过请求参数传入）
    // 调用链：VulnChild -> VulnParent::executeCommand -> popen（漏洞点）
    std::string result1 = child.executeCommand(cmd);
    std::cout << "[外部参数] 命令执行结果: " << result1 << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_cmd = "echo hardcoded_cmd_exec_test";
    // 调用链：VulnChild -> VulnParent::executeCommand -> system（漏洞点）
    std::string result2 = child.executeCommand(hardcoded_cmd);
    std::cout << "[硬编码] 命令执行结果: " << result2 << std::endl;
}
