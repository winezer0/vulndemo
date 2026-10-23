# C++ 词法绑定生命周期

`caller.cpp` 的外层 Alpha 接收者与内层 Beta 隔离；测试只修改外层参数类型为 Beta。`captures.cpp` 固定 lambda 初始化捕获的声明及调用身份。`namespaces.cpp` 包含 namespace alias、using declaration/directive、普通变量遮蔽和两个 arity 的重载。

`boundaries.cpp` 分别保留模板依赖调用、函数指针、可调用对象、宏、ADL、继承歧义及函数模板实例化的证据边界；没有完整派发证据时不能按裸名确认源码目标。所有文件由跨文件 Memory/SQLite 编辑、删除、恢复、重复解析及重开测试复用。
