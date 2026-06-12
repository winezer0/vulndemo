#include "common/vuln_child.h"
#include <iostream>
#include <string>

// 代码执行漏洞 - 纯子类继承调用测试文件
// 测试场景：通过子类 VulnChild 继承父类 VulnParent 的代码执行漏洞方法

void code_exec_sub_class_test(const std::string& code) {
    std::cout << "=== 代码执行漏洞 - 子类继承调用测试 ===" << std::endl;

    VulnChild child;

    // 场景1：外部可控数据源（通过请求参数传入）
    // 调用链：VulnChild -> VulnParent::executeCode -> popen python3 -c（漏洞点）
    std::string result1 = child.executeCode(code);
    std::cout << "[外部参数] 代码执行结果: " << result1 << std::endl;

    // 场景2：硬编码无外部数据源
    std::string hardcoded_code = "print('hardcoded_code_exec_test')";
    std::string result2 = child.executeCode(hardcoded_code);
    std::cout << "[硬编码] 代码执行结果: " << result2 << std::endl;
}
