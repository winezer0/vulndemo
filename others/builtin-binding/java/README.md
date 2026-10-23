# Java 词法绑定反例矩阵

J2 独立反例矩阵，现用于 Java producer、消费者及 resolver 的默认验收。

`matrix.json` 固定 10 份源码、52 个查询点、2 个完整方法目标和 1 个显式字段访问目标。位置均为 1-based UTF-8 字节列；声明身份由源码文件和声明名字的真实位置组成，重载同时保留额外声明位置。预期来自语言规则和人工编写的案例，不从生产查询结果生成。

| 源码 | 边界 |
| --- | --- |
| Namespaces.java | 同名类型、值、方法；同一调用位置分别查询三个名字空间；重载位置集合 |
| Initialization.java | 声明之前、自己的初始化、多声明符；普通 for 的初始化/条件/更新/主体/退出；增强 for 的 iterable 和目标 |
| Closures.java | lambda 参数遮蔽字段、捕获外层局部、嵌套块退出后恢复 |
| Resources.java | 自己的初始化、后续资源尚不可见、先前资源、try 主体、lambda 捕获、catch/finally 和 catch 参数退出 |
| Types.java | 类与方法类型参数、局部类型声明之前/自身/之后 |
| Members.java | 静态上下文中的名字占用、同名成员方法与静态导入、内部类、未知父类边界 |
| Imports.java | 显式静态导入，无同名成员遮蔽 |
| Patterns.java | instanceof 真/假分支、&&、带否定的 ||、提前 return 后的流作用域 |
| Unsupported.java | grammar 可解析的 record pattern；本批先要求不完整快照和诊断，查询必须 unknown |
| Calls.java | 零参数/单参数重载、显式 this 接收者、同名参数与 this 字段 |

初始化自引用、静态方法读取实例字段、不可适用的成员重载，以及资源退出后的名字引用等源码有意包含编译期反例。这里检验解析后的名字占用和保守边界，不以 javac 编译成功代替词法断言。成员名字占用与访问权限、重载适用性分别处理。

## 测试与当前状态

在工作区根目录加载 `script/go-workspace-environment.ps1` 并调用 `Initialize-WorkspaceGoEnvironment` 后：

- `go test ./parser/languages/java -run TestJavaLexicalMatrixGrammar -count=1`：正常门禁，验证全部源码可由真实 grammar 解析、查询位置、声明引用、重载位置和完整目标签名。
- `go test ./parser/languages/java/syntax -run TestLexicalGrammarFields -count=1`：11 种 AST 形状，特别固定 catch 无 type 字段、instanceof 的 name 字段、无字段的 type/record pattern，以及 lambda 参数的三种结构。
- `go test ./parser/languages/java -run TestJavaLexicalMatrixAcceptance -count=1`：默认语义门禁，10 例、52 个独立查询及持久化往返通过；已移除原红灯构建标签。

`lifecycle/` 的七个跨文件源码用于 `codeindex/indexer.TestJavaLexicalLifecycle`：校验参数及块遮蔽、重载、静态导入、内部类、继承、模式及不完整边界的完整目标身份。结构调用、arg_of 的参数归属和 returns_to 的方法身份一致；Memory/SQLite 完整边 JSON 和文件快照在编辑、恢复、重复解析、删除、clone/reopen 后一致。

record/switch 模式、循环及三元表达式中的模式流保守返回 unknown 并附诊断；本批不把未知区域宣称为完全解析。

规则核对：[JLS §6.3–6.5](https://docs.oracle.com/javase/specs/jls/se25/html/jls-6.html)、[实际 tree-sitter-java v0.23.5 节点定义](https://raw.githubusercontent.com/tree-sitter/tree-sitter-java/v0.23.5/src/node-types.json)。grammar 支持范围以仓库实际测试为准。
