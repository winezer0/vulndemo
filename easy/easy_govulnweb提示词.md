请在当前项目的 govulnweb 目录下，编写一套基于 Go 标准库的极简 Web 测试代码，用于演示跨文件调用、接口、结构体嵌入（继承）、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：main.go（HTTP 入口）、func_call.go（跨文件调用函数）；
2. common 包存放两个结构体：VulnParent（父结构体，包含命令执行漏洞方法）、VulnChild（子结构体，嵌入 VulnParent）；
3. iface 包单独存放接口定义文件 vuln_runner.go；
4. cmd_exec 目录存放接口实现结构体文件 interface_impl.go，该结构体嵌入 common.VulnChild 并隐式实现 iface.VulnRunner 接口。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 Exec 方法中使用 exec.Command("sh", "-c", cmd) 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 结构体嵌入（继承）关系
   - VulnChild 嵌入 VulnParent，从而继承 Exec 方法；
   - InterfaceImpl 嵌入 VulnChild，从而间接继承 Exec 方法；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层嵌入链。

3. 接口定义与隐式实现
   - iface 包定义 VulnRunner 接口，仅包含 Run(string) string 方法签名；
   - cmd_exec 包的 InterfaceImpl 结构体实现 Run 方法，在其内部调用继承来的 Exec 方法，从而隐式满足 VulnRunner 接口。

4. 两条调用路径
   - 路径一（纯结构体）：HTTP 请求 → RunByChild(cmd) → VulnChild.Run() → VulnParent.Exec() → 命令执行；
   - 路径二（接口调用）：HTTP 请求 → RunByInterface(cmd) → 将 InterfaceImpl 赋值给 VulnRunner 接口变量 → 接口调用 Run() → InterfaceImpl.Run() → VulnParent.Exec() → 命令执行；
   - func_call.go 中编写 RunByChild 和 RunByInterface 两个独立函数，分别演示两条路径。

5. HTTP 入口
   - main.go 使用 net/http 标准库监听 :8080；
   - /exec 路由接收 cmd 参数和 mode 参数；
   - mode=impl 时走接口调用路径，其他值走纯结构体调用路径；
   - 将执行结果直接返回给浏览器。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的结构体定义或函数，无多余代码；
2. 包导入路径使用相对项目根的正确路径，确保编译通过；
3. 结构体方法、接口实现、跨文件调用关系通过函数签名和嵌入关系自然体现，无需额外注释；
4. 代码总量控制在 6 个文件、约 60 行以内。

四、输出要求
1. 先输出完整的目录树结构；
2. 再按目录顺序，依次输出每一个文件的完整可运行 Go 代码；
3. 代码保证可直接 go run 运行，无需额外依赖（仅使用 Go 标准库）。

---

## 补充目录结构参考
```
govulnweb/
├── main.go                     # HTTP 入口，/exec 路由分发
├── func_call.go                # RunByChild / RunByInterface 两个跨文件调用函数
├── common/
│   ├── vuln_parent.go          # VulnParent 结构体 + Exec 方法（命令执行漏洞点）
│   └── vuln_child.go           # VulnChild 嵌入 VulnParent + Run 方法
├── iface/
│   └── vuln_runner.go          # VulnRunner 接口定义
└── cmd_exec/
    └── interface_impl.go       # InterfaceImpl 嵌入 VulnChild，实现 Run 方法
```
