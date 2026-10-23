# C# 词法绑定生命周期样例

`Caller.slot` 字段与内层块的局部 `slot` 类型不同。验收独立断言第 6、9、11 行的调用分别指向 Service、Other、Service 的声明；编辑只替换内层声明类型。`Uncertain` 的 dynamic 接收者必须保留未解析状态。

样例用于 Memory/SQLite 完整图比较、增量编辑与恢复、删除与恢复、重复解析、Memory clone 和 SQLite 重开。

`Captures` 包含一个局部函数与同一行的两个 lambda。它们调用同一方法，但必须保留三个独立的调用者身份，并分别捕获第 3 行的 `client` 参数。

`Generic` 的类型参数与源码类 `Service` 同名。即使约束允许该成员调用，也不能仅按类型参数的拼写确认到源码类；约束分派未建模时保留未知。
