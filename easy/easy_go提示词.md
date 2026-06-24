请在当前项目的 go 目录下，编写一套基于 Go 的极简测试代码，用于演示跨文件调用、接口、结构体嵌套（模拟继承）、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：main.go（入口）、func_call.go（跨文件调用结构）；
2. common 目录存放两个结构体：vuln_parent.go（基础结构，包含命令执行漏洞方法）、vuln_child.go（组合结构，嵌套 vuln_parent）；
3. iface 目录单独存放接口定义文件 vuln_runner.go；
4. cmd_exec 目录存放接口实现文件 interface_impl.go，该结构嵌套 common.VulnChild 并实现 iface.VulnRunner 接口。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 Exec 方法中使用 os/exec 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 结构体嵌套关系（模拟继承）
   - VulnChild 嵌套 VulnParent，从而继承 Exec 方法；
   - InterfaceImpl 嵌套 VulnChild，从而间接继承 Exec 方法；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层嵌套链。

3. 接口定义与实现
   - iface 目录定义 VulnRunner 接口，仅包含 Run(cmd string) 方法签名；
   - cmd_exec 目录的 InterfaceImpl 结构实现 Run 方法，在其内部调用继承来的 Exec 方法，从而满足 VulnRunner 接口。

4. 两条调用路径
   - 路径一（纯嵌套调用）：main 函数 → RunByChild(cmd) → VulnChild.Exec() → 命令执行；
   - 路径二（接口调用）：main 函数 → RunByInterface(cmd) → 将 InterfaceImpl 赋值给 VulnRunner 接口变量 → 接口调用 Run() → InterfaceImpl.Run() → VulnParent.Exec() → 命令执行；
   - func_call.go 中编写 RunByChild 和 RunByInterface 两个独立函数，分别演示两条路径。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的结构定义或方法，无多余代码；
2. 结构方法、接口实现、跨文件调用关系通过方法签名和嵌套关系自然体现，无需额外注释；
3. 代码总量控制在 6 个文件、约 60 行以内。
