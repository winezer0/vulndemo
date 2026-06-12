#pragma once

#include "vuln_parent.h"
#include <string>

// 独立子类声明，继承 VulnParent
// 通过继承复用父类的漏洞方法，同时可扩展自身行为
class VulnChild : public VulnParent {
public:
    VulnChild() = default;
    ~VulnChild() override = default;

    // 子类可重写父类方法，也可直接调用父类漏洞方法
    std::string executeCommand(const std::string& cmd) override;
    std::string executeCode(const std::string& code) override;
    std::string fetchUrl(const std::string& url) override;
};
