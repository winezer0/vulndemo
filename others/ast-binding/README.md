# AST 精确绑定回归样例

ReceiverJava.java、ReceiverCSharp.cs、receiver.php、Receiver.kt、receiver.cpp 提供真实实例方法 this/$this 使用及调用 receiver。receiver.js/ts 验证 class instance method 的 this 精确范围，并排除 static method；对象方法、动态函数和箭头捕获保留缺口。decomposition.kt/cpp 验证独立分解事实缺失时的 BindingGaps。生产查询回归已通过；新增 mgsast Memory/SQLite 绑定及篡改回归仅写入源码，尚未运行或编译。

仅验证语法出现位置、参数槽位、返回接收位置及缺口反馈，不代表漏洞真值。

`projections.py/js/ts/php/rs` 与 `Projections.cs` 覆盖返回槽 0 下的容器路径 `[1]`、解构接收及成员写入。当前生产 SourceOperations 查询回归已执行；对应 mgsast 跨层测试仍暂停，不能把查询通过写成审计验收通过。

- Go：第二参数、可变参数及展开、接收者、多返回值、忽略槽位、同一行多次调用、注释与字符串、重复表达式、嵌套函数。
- Python：具名参数、仅关键字参数、重复绑定及无法确定形状的展开参数。

测试通过真实 Parser 和 CodeIndex 查询建立证据，并验证提交、序列化、Journal、报告投影和回执重验。`occurrences.go` 中同一行多个调用是必要反例，请勿格式化拆行。
