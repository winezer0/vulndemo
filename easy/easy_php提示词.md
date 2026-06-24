请在当前项目的 php 目录下，编写一套基于 PHP 的极简 Web 测试代码，用于演示跨文件调用、接口、类继承、命令执行等核心关系。代码务必精简，每个文件不超过 15 行，职责单一。

一、整体目录与文件拆分规则
1. 根目录保留两个文件：index.php（HTTP 入口）、FuncCall.php（跨文件调用类）；
2. common 目录存放两个类：VulnParent（父类，包含命令执行漏洞方法）、VulnChild（子类，继承 VulnParent）；
3. iface 目录单独存放接口定义文件 VulnRunner.php；
4. cmd_exec 目录存放接口实现类文件 InterfaceImpl.php，该类继承 common\VulnChild 并实现 iface\VulnRunner 接口。

二、代码功能与调用关系要求
1. 命令执行漏洞点
   - 在 VulnParent 的 exec 方法中使用 shell_exec(cmd) 执行系统命令；
   - 该方法是所有调用链的最终漏洞入口。

2. 类继承关系
   - VulnChild 继承 VulnParent，从而继承 exec 方法；
   - InterfaceImpl 继承 VulnChild，从而间接继承 exec 方法；
   - 形成 InterfaceImpl → VulnChild → VulnParent 的三层继承链。

3. 接口定义与实现
   - iface 目录定义 VulnRunner 接口，仅包含 run(string): string 方法签名；
   - cmd_exec 目录的 InterfaceImpl 类实现 run 方法，在其内部调用继承来的 exec 方法，从而满足 VulnRunner 接口。

4. 两条调用路径
   - 路径一（纯类继承）：HTTP 请求 → runByChild(cmd) → VulnChild.run() → VulnParent.exec() → 命令执行；
   - 路径二（接口调用）：HTTP 请求 → runByInterface(cmd) → 将 InterfaceImpl 赋值给 VulnRunner 接口变量 → 接口调用 run() → InterfaceImpl.run() → VulnParent.exec() → 命令执行；
   - FuncCall.php 中编写 runByChild 和 runByInterface 两个独立方法，分别演示两条路径。

5. HTTP 入口
   - index.php 通过 $_GET 接收 cmd 参数和 mode 参数；
   - mode=impl 时走接口调用路径，其他值走纯类继承调用路径；
   - 将执行结果直接输出到浏览器。

三、代码规范要求
1. 每个文件功能单一，仅包含必要的类定义或方法，无多余代码；
2. 使用 require_once 进行文件引用，确保正确的加载顺序；
3. 类方法、接口实现、跨文件调用关系通过方法签名和继承关系自然体现，无需额外注释；
4. 代码总量控制在 6 个文件、约 60 行以内。

四、输出要求
1. 先输出完整的目录树结构；
2. 再按目录顺序，依次输出每一个文件的完整可运行 PHP 代码；
3. 代码保证可直接在 PHP 环境中运行，无需额外依赖。

---

## 补充目录结构参考
```
php/
├── index.php                    # HTTP 入口，接收 cmd 和 mode 参数
├── FuncCall.php                 # runByChild / runByInterface 两个跨文件调用方法
├── common/
│   ├── VulnParent.php           # VulnParent 类 + exec 方法（命令执行漏洞点）
│   └── VulnChild.php            # VulnChild 继承 VulnParent + run 方法
├── iface/
│   └── VulnRunner.php           # VulnRunner 接口定义
└── cmd_exec/
    └── InterfaceImpl.php        # InterfaceImpl 继承 VulnChild，实现 run 方法
```