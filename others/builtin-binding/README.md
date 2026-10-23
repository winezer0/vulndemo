# 内置名称绑定回归

- `java`：方法、类型、值类别及精确声明位置，局部/成员/导入与未知调用边界，双存储及增量验收样例。
- `csharp`：词法遮蔽、类型别名、闭包、泛型及不确定接收者，生命周期验证调用和参数/返回流使用相同声明身份。
- `rust/lifecycle`：模块别名、接收者、顺序遮蔽、闭包捕获和泛型未知边界，含 Memory/SQLite 编辑、删除、恢复、重开与重复解析完整图对照。
- `php/lifecycle`：同文件不同 namespace 同名函数、类型/函数导入别名、普通函数与变量闭包、显式/箭头捕获，含调用身份、参数和返回流的双存储生命周期对照。
- `python-lexical`：整函数局部分类、参数、删除、global/nonlocal、类方法与推导式；独立状态标记用于双存储、真实编辑、克隆与重开对照。
- `python-lexical-review`：属性调用后的 global/nonlocal 合法性及真实赋值改动对照。
- `javascript-lexical-review` / `typescript-lexical-review`：全局写入、方法计算名、实例字段时机；提供完整调用图、参数默认值/静态字段编辑、文件删除恢复和 clone/reopen 回归基线。
- `javascript-lexical` / `typescript-lexical`：共享 TDZ、var/函数提升、解构、兄弟块与循环退出；TS 额外验证纯类型名字不遮蔽运行时调用。
- `go-lexical`：初始化右侧、同一行声明、参数、命名返回值、嵌套/兄弟块、控制语句、闭包、常量及局部类型的名字遮蔽；`lexical:` 注释提供独立预期。部分调用刻意非法，用于验证禁止回退内置函数。
- `go-lexical-package`：调用文件保持不变时，同包变量新增遮蔽、恢复、删除和再次恢复的增量对照基线。

- `kotlin-local`：跨文件顶层 `run` 应绑定本地声明。
- `go-local`：跨文件自定义 `len` 应覆盖预声明名称，不得被低价值内置调用过滤。
- `kotlin-unknown`：无法确认成员方法时保留未解析，不能绑定全局内置 `run`。
- `kotlin-member`：真实成员 `Alpha.let` 优先于目录中的通配扩展方法。
- `kotlin-import`：显式导入别名 `runService` 精确选择 `alpha.execute`，排除 `beta.execute`；双存储重复解析和重索引后保持完整目标身份。

`kotlin-member` 及 `receiver-flow/kotlin` 的类成员末尾保留换行，以避免当前 grammar 的缺失自动分号；Kotlin 解析测试对这些 fixture 强制校验无错语法及完整词法快照。

供 CodeIndex 的 Memory/SQLite 全量及增量回归使用，不依赖外部 semantic Provider。

## 跨语言边界矩阵

- `c/lifecycle`：普通标识符与 tag/label 隔离，精确头文件原型、嵌套接收者字段、函数指针及条件编译/宏未知边界；覆盖双存储编辑、删除、恢复、重复解析和重开。
- `cpp/lifecycle`：块级接收者与 lambda 初始化捕获、namespace alias、using 与重载声明集合，以及 ADL、继承歧义、模板、函数指针、可调用对象和宏的未知边界；复用完整双存储生命周期门禁。

- `go-grouped` / `go-grouped-match`：分组形参实际为两个参数；不兼容调用保留排除证据，不能回退内置。
- `go-local-before` / `go-local-after`：Go 局部声明前后的可见性。
- `go-import-alias`：包别名占据内置名称时，即使调用非法也不能绑定内置。
- `kotlin-overload` / `kotlin-ambiguous`：保留全部重载，分别验证唯一兼容与多个兼容候选。
- `kotlin-default` / `kotlin-lambda`：默认参数和尾随 lambda。
- `kotlin-extension`：扩展函数接收者候选保留，不回退通配内置；访问边界证据不足时不确认目标。
- `python-source` / `python-parameter` / `python-local-before` / `python-local-after` / `python-import`：同名声明、参数、整个函数局部作用域及导入别名。
- `javascript-*` / `typescript-*`：同名参数、局部声明前后、函数提升、导入别名。声明前 const 调用处于暂时性死区，图中仅保留未确认 AST 关系，不能视为运行时成功调用。

矩阵逐项检查完整目标 ID、origin、resolution state、候选 ID 和参数冲突排除证据，并比较两种存储全量、调用文件重索引、声明文件重索引结果。

声明变更测试将 Go/Kotlin fixture 复制到测试临时目录，反复修改、恢复参数数量或重载集合，并在每个阶段与新建全量索引比较，原始 fixture 保持可复用。
