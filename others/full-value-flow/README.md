# FullValueFlow 源码基线

2026-11-02 当前验收增量：11 语言真实源码、Memory/SQLite 双向、条件引用和捕获时序共 1704 条观察通过；实际 Rule 121 场景、HTTP 254 场景通过。包括保存任务实例、返回函数值、可变捕获、C pthread 通信及 PHP generator/Fiber。Fiber 明确区分构造、start 和 getReturn，附未启动、start 返回值、异实例及覆盖反例。上下文预算、外部调度、Fiber suspend/resume 和无法解析的动态关系保留 candidate/incomplete 边界。最终四模块与资源验收见 `TODO/FullValueFlow最终验收-20261102.md`；下文计数为各历史增量，不能相加为当前独立样本数。

W14 已完成：四模块完整测试/vet 采用 v91 前三模块与 v98 MGSAST 组合验收；v91 整轮失败单独保留。v99 语言覆盖率、v100 消费及显式 E2E、v101 消费 race、v102/v103 各三轮资源/warm 均通过。完整记录见[最终验收](../../../TODO/FullValueFlow最终验收-20261102.md)，原始证据根目录为 `C:/ISEC/Deving/full-value-flow-acceptance/20261102/W14`。下文“待完成”等措辞仅属于所标注的历史轮次。

## 当前代表性能力与边界

以下各行已通过真实 parser → CodeIndex → Memory/SQLite 正反向查询；生产 Rule 与 HTTP 另有各语言代表性消费回归。`supported` 指本行有界样例已执行，不表示任意程序均可证明；具体路径仍分别保留 verified、candidate 和 incomplete。实际结果以 `W14/matrix.json` 和 `W14/consumers.json` 为准。

| 语言 | 已执行的有界机制与真实源码 | candidate / incomplete 边界 |
| --- | --- | --- |
| Go | [字段、容器、指针/切片与闭包](go/flow.go)，[channel 实例与实参](go/boundary.go) | 动态键、循环合流、可能别名；外部通信与调度不假设顺序 |
| JavaScript | [对象、数组与函数值](javascript/flow.js)，[Promise/await](javascript/async.js) | 动态属性、可能引用及未证明的库协议 |
| TypeScript | [对象、数组与函数值](typescript/flow.ts)，[Promise/await](typescript/async.ts) | 类型注解不替代实例身份；动态属性和外部调度 |
| Python | [属性、类型键、别名与回调](python/flow.py)，[coroutine/task](python/async.py) | 动态调用、外部调度和无法闭合的对象关系 |
| PHP | [数组复制、引用与回调](php/flow.php)，[generator](php/generator.php)，[Fiber](php/fiber.php) | Fiber suspend/resume 载荷及外部调度；start 返回值不等同最终返回 |
| Java | [实例、数组/容器与 lambda](java/Flow.java)，[保存 Future](java/AsyncFlow.java)，[调度回调](java/AsyncCallback.java) | reference_nested_call 四条观察保留 value_context_budget 截断 |
| Kotlin | [容器、引用与 lambda](kotlin/Flow.kt)，[suspend](kotlin/async.kt) | 未确认的下标协议允许候选误报，不凭异下标强剪枝 |
| C# | [数组、引用与委托](csharp/Flow.cs)，[Task/await](csharp/async.cs)，[调度回调](csharp/AsyncCallback.cs) | 自定义 indexer、可能引用与未闭合的调度关系 |
| C | [数组、指针与函数指针](c/flow.c)，[pthread 创建—句柄—join](c/thread.c) | 指定初始化、未知指针及并发顺序保留候选边界 |
| C++ | [数组、引用、复制与 lambda](cpp/flow.cpp)，[std::async/future](cpp/async.cpp) | 自定义下标、ADL、模板与函数指针不能伪装确定绑定 |
| Rust | [数组、借用、复制与闭包](rust/flow.rs)，[Future/await](rust/async.rs)，[spawn/join](rust/thread.rs) | 重复数组初始化、未知接收者及未闭合的借用/调度关系 |

同名函数、多次调用、互异对象/键、覆盖写、条件引用、返回回调和捕获时序均保留隔离反例。回调参数解析按明确 receiver 证据区分值调用与成员调用；C# 直接委托可以使用 method 节点表示，不能仅凭节点种类阻断。

## 历史增量记录

以下“当前”“尚未完成”和计数均指对应历史轮次；保留基线演进，不替代上面的最新能力及最终验收记录。

`go/flow.go` 与 `go/cases.json` 是共享的源码及独立目标标注。标注不是当前实现结果，W01 允许出现缺失路径和错误关联；观察数据与目标分别保存。

查询 source 为具名参数节点，target 为具名局部结果节点，目标槽位 0 表示不指定；不将其冒充生产漏洞 Source/Sink 识别。`verified`/`candidate` 为未来路径证明目标，不能用当前关系的 resolved 状态替代。`none` 指本样例能证明互异或覆盖后的无关联，不代表任意未知路径安全。

Go 动态 key 分别由调用方传入 key/readKey，无法证明相同也不能证明互异；空字符串键为独立静态键。async 样例通过真实 channel 传值，不能因存在 goroutine 就推断传播。当前 24 场景是 W01 的初始源码基线，尚未覆盖全部规模/资源、生产 overlay、Rule 或 WebUI 验收。

Go 当前共 38 个场景，包含边界样例、具名启动闭包及 channel 参数、嵌套字段和字段下容器键；base/full、Memory/SQLite、正反向产生 304 条观察。W01 原始源码已独立归档；`range_value` 的观测点已纠正为循环后的结果，详见 TODO 实施进度文档。channel 候选不能等同于已验证执行顺序。

其他十个语言当前有 388 条双后端双方向观察：共同字段、跨调用与隔离门禁，以及 JS/TS/Python/PHP 初始化、Python 类型键和别名、PHP 数组值复制，并加入同步直接回调与未调用隔离。各语言机制覆盖不同，高阶回调、捕获时序与 async 尚未收口；生产消费链与最终完整验收仍在实施中。

后续容器门禁已扩展至 520 条观察：Java/C# 各增加 10 个数组场景，Rust 增加 13 个数组、借用和值复制场景。准确索引、互异索引、别名、覆盖写、未知索引、初始化调用隔离均使用真实源码和实际读取值节点；Rust 重复初始化另保留候选传播，不将长度当作元素。该计数只描述当前固定矩阵，不代表所有语言的容器、指针或库 API 已覆盖。

11 语言另有高阶回调参数门禁，覆盖实际目标候选、双向调用/污点路径、跨调用隔离、Memory 克隆、SQLite 文件关闭重开，以及同一查询引擎上的源码更新/删除/重建。临时候选与回调上下文不得写回图。返回回调、捕获可变值时序和各语言 async 继续实施，不能将 incomplete 算作成功检出。

C/C++ 后续各增加 13/17 个场景，十语言固定矩阵现为 640 条观察。其中 C/C++ 当前 176 条双后端双方向观察通过，覆盖原生数组、指针别名、C++ 引用和值复制、调用初始化、未知索引和真实八进制键；C 指定初始化仍按候选保留。完整指针、标准库容器及异步机制尚未完成。

Go 后续新增 6 个循环场景，总计 44 个：先读后写、多阶段及嵌套循环保留跨迭代候选路径，互异字段及循环后写入保持隔离。完整 base/full、双后端双方向矩阵的 352 条观察已在 `full-value-flow-native-integration-v4.jsonl` 通过；最终冻结回归继续执行。

后续 Kotlin 增加 8 个下标候选场景，Kotlin/C#/C++ 各增加实际忽略索引的自定义 indexer，十语言矩阵现为 684 条观察。协议未确认时不凭不同选择器剪枝，也不以安全覆盖强杀死旧候选；Kotlin 的互异索引和安全覆盖样例因此允许候选误报，并明确标注 `container_protocol_unconfirmed`，不能解释为确定性污点路径。精确库容器及工厂绑定仍待实施。

JS/TS/Python/C#/Rust/Kotlin 新增 `async` 源码，覆盖原生 await、嵌套返回、丢弃结果与独立安全调用。十语言矩阵扩大为 780 条双后端双方向观察并已通过；parser 另检查 Base+Full 与 Full-only 的实际调用结果位置一致性。此结果仅覆盖原生等待值绑定，不代表 Task/Promise/Future 库调度及线程通信已完成。
