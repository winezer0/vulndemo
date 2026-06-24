请在当前项目的 rust 目录下，编写一套基于 Rust 的极简测试代码，用于演示跨文件调用、Trait（接口）、结构体组合、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：main.rs（入口）、func_call.rs（跨文件调用模块）；
2. common 目录存放两个模块：vuln_parent.rs（基础结构，包含命令执行漏洞方法）、vuln_child.rs（组合结构，包含 VulnParent）；
3. iface 目录单独存放 Trait 定义文件 vuln_runner.rs；
4. cmd_exec 目录存放接口实现文件 interface_impl.rs，该结构包含 common::VulnChild 并实现 iface::VulnRunner Trait。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 exec 方法中使用 std::process::Command 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 结构体组合关系（模拟继承）
   - VulnChild 包含 VulnParent；
   - InterfaceImpl 包含 VulnChild；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层组合链。

3. 接口定义与实现
   - iface 目录定义 VulnRunner Trait，仅包含 run(&self, cmd: &str) 方法签名；
   - cmd_exec 目录的 InterfaceImpl 结构实现 run 方法，在其内部调用组合的 exec 方法。

4. 两条调用路径
   - 路径一（纯组合调用）：main 函数 → func_call::run_by_child(cmd) → VulnChild.parent.exec() → 命令执行；
   - 路径二（接口调用）：main 函数 → func_call::run_by_interface(cmd) → 将 InterfaceImpl 作为 &dyn VulnRunner 传递 → 接口调用 run() → InterfaceImpl.run() → VulnParent.exec() → 命令执行；
   - func_call.rs 中编写 run_by_child 和 run_by_interface 两个独立函数，分别演示两条路径。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的结构定义或方法，无多余代码；
2. 使用 mod 和 pub 关键字正确管理模块可见性；
3. 代码总量控制在多个小文件、约 80 行以内。
