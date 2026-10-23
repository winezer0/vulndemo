# C 词法绑定生命周期

`api.h` 保留真实原型声明，`alpha.c`、`beta.c` 提供不同实现。`caller.c` 的外层 Alpha 接收者与内层 Beta 同名局部验证精确声明类型，两个 alpha 调用提供参数和返回流对照。测试复制到临时目录后将外层 Alpha 改为 Beta、alpha 调用改为 beta，比较增量与全量图，再恢复、删除、重开。

`shadow.c` 同时保留 choose tag、函数、函数指针参数和函数 label；间接调用不能绑定到同名源码函数。`conditional.c` 的预处理分支不提供确定配置，`macro.c` 的宏展开未知，均不能被名字解析提升为确定的源码或内置目标。
