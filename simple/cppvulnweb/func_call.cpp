#include "func_call.h"
#include <cstdio>
#include <cstdlib>
#include <string>

// Windows兼容性：popen/pclose在MSVC中名为_popen/_pclose
#ifdef _WIN32
#define popen _popen
#define pclose _pclose
#endif

// 跨文件函数调用测试函数实现
// 使用poppen执行命令，存在命令注入漏洞
std::string funcCallTest(const std::string& input) {
    std::string cmd = "echo " + input;
    char buffer[128];
    std::string result;
    
    // 使用poppen执行命令，未对input进行过滤，存在命令注入漏洞
    FILE* pipe = popen(cmd.c_str(), "r");
    if (!pipe) {
        return "Error: Failed to execute command";
    }
    
    while (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
        result += buffer;
    }
    
    pclose(pipe);
    return result;
}

// 硬编码漏洞测试函数实现
// 包含硬编码的命令执行漏洞
std::string hardcodedVulnTest() {
    // 硬编码命令，存在命令执行漏洞
    std::string hardcoded_cmd = "echo hardcoded_vulnerability_test";
    
    // 使用system执行硬编码命令
    int ret = system(hardcoded_cmd.c_str());
    
    if (ret == 0) {
        return "Hardcoded command executed successfully: " + hardcoded_cmd;
    } else {
        return "Failed to execute hardcoded command";
    }
}