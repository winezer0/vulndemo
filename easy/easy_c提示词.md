请在当前项目的 c 目录下，编写一套基于 C 语言的极简测试代码，使用结构体和函数指针模拟跨文件调用、接口、继承、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：main.c（入口）、func_call.c（跨文件调用函数）及其头文件；
2. common 目录存放两个结构体定义及实现：vuln_parent.c/h（基础结构，包含命令执行漏洞函数指针）、vuln_child.c/h（组合结构，嵌套 vuln_parent）；
3. iface 目录单独存放接口定义文件 vuln_runner.h；
4. cmd_exec 目录存放接口实现文件 interface_impl.c/h，该结构嵌套 common 的 VulnChild 并包含 iface 的 VulnRunner 函数指针。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 exec 函数指针指向的实现中使用 system(cmd) 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 结构体嵌套关系（模拟继承）
   - VulnChild 嵌套 VulnParent；
   - InterfaceImpl 嵌套 VulnChild；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层嵌套链。

3. 接口定义与实现
   - iface 目录定义 VulnRunner 结构体，仅包含 run(void*, const char*) 函数指针；
   - cmd_exec 目录的 InterfaceImpl 结构实现 run 函数，在其内部调用嵌套的 exec 函数。

4. 两条调用路径
   - 路径一（纯嵌套调用）：main 函数 → run_by_child(cmd) → child.parent.exec() → 命令执行；
   - 路径二（接口调用）：main 函数 → run_by_interface(cmd) → 通过 runner.run() → impl_run() → parent.exec() → 命令执行；
   - func_call.c 中编写 run_by_child 和 run_by_interface 两个独立函数，分别演示两条路径。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的结构定义或方法，无多余代码；
2. 代码总量控制在多个小文件、约 80 行以内。
