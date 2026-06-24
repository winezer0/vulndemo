请在当前项目的 cs 目录下，编写一套基于 C# 的极简测试代码，用于演示跨文件调用、接口、类继承、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：Program.cs（入口）、FuncCall.cs（跨文件调用类）；
2. common 目录存放两个类：VulnParent.cs（父类，包含命令执行漏洞方法）、VulnChild.cs（子类，继承 VulnParent）；
3. iface 目录单独存放接口定义文件 IVulnRunner.cs；
4. cmd_exec 目录存放接口实现类文件 InterfaceImpl.cs，该类继承 common.VulnChild 并实现 iface.IVulnRunner 接口。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 Exec 方法中使用 Process.Start 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 类继承关系
   - VulnChild 继承 VulnParent，从而继承 Exec 方法；
   - InterfaceImpl 继承 VulnChild，从而间接继承 Exec 方法；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层继承链。

3. 接口定义与实现
   - iface 目录定义 IVulnRunner 接口，仅包含 Run(string cmd) 方法签名；
   - cmd_exec 目录的 InterfaceImpl 类实现 Run 方法，在其内部调用继承来的 Exec 方法。

4. 两条调用路径
   - 路径一（纯类继承）：Program 类 → FuncCall.RunByChild(cmd) → VulnChild.Exec() → 命令执行；
   - 路径二（接口调用）：Program 类 → FuncCall.RunByInterface(cmd) → 将 InterfaceImpl 赋值给 IVulnRunner 接口变量 → 接口调用 Run() → InterfaceImpl.Run() → VulnParent.Exec() → 命令执行；
   - FuncCall.cs 中编写 RunByChild 和 RunByInterface 两个独立静态方法，分别演示两条路径。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的类定义或方法，无多余代码；
2. 代码总量控制在 6 个文件、约 60 行以内。
