#pragma once

#include <string>

// 独立纯虚类定义文件（接口）
// C++中使用纯虚类模拟接口，所有方法为纯虚函数
class IVulnInterface {
public:
    virtual ~IVulnInterface() = default;

    // 命令执行接口方法
    virtual std::string executeCommandViaInterface(const std::string& cmd) = 0;

    // 代码执行接口方法
    virtual std::string executeCodeViaInterface(const std::string& code) = 0;

    // SSRF接口方法
    virtual std::string fetchUrlViaInterface(const std::string& url) = 0;
};

// 命令执行接口实现类声明
class CmdExecInterfaceImpl : public IVulnInterface {
public:
    std::string executeCommandViaInterface(const std::string& cmd) override;
    std::string executeCodeViaInterface(const std::string& code) override;
    std::string fetchUrlViaInterface(const std::string& url) override;
};

// 代码执行接口实现类声明
class CodeExecInterfaceImpl : public IVulnInterface {
public:
    std::string executeCommandViaInterface(const std::string& cmd) override;
    std::string executeCodeViaInterface(const std::string& code) override;
    std::string fetchUrlViaInterface(const std::string& url) override;
};

// SSRF接口实现类声明
class SsrfInterfaceImpl : public IVulnInterface {
public:
    std::string executeCommandViaInterface(const std::string& cmd) override;
    std::string executeCodeViaInterface(const std::string& code) override;
    std::string fetchUrlViaInterface(const std::string& url) override;
};
