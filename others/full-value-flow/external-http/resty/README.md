# Resty 内容传播用例

专用于语法解析、索引与图查询；运行时生命周期测试使用本地自定义 transport，不发起外部网络请求。

依赖固定为 `github.com/go-resty/resty/v2 v2.16.5`，API 依据为 [对应版本源码](https://github.com/go-resty/resty/tree/v2.16.5)。模块校验和：`h1:hBKqmWrr7uRc3euHVqmh1HTHcKn99Smr7o5spptdhTM=`。

基础组覆盖普通与链式请求、Body/String/RawBody 消费、别名、双响应隔离和未知 Result 语义。Result 保留候选传播，不视为精确解码模型。

生命周期组覆盖响应/请求 middleware 捕获值、自定义 transport、条件配置及条件别名、helper 配置的参数和返回、忽略 helper 返回值的副作用、请求级配置、request 创建后的 client 配置、重试 hook、不解析正文、部分读取失败和响应正文覆盖。来源与请求 URL 分开，避免用 URL 路径掩盖配置来源丢失。精确默认模型只适用于有充分默认状态证据的实例；其余场景保留候选和结构化缺口，未知 error 槽位也可保留候选，不标成已验证正文。

`lifecycle_runtime_test.go` 使用真实 v2.16.5 验证响应 hook、请求前 middleware、重试、本地 RawBody 部分读取失败及请求前失败返回 nil response。可用 `GOWORK=off go test -mod=readonly . -count=1` 独立执行。图侧测试分别检查 Memory/SQLite 重开、双向可达、候选证明、原始诊断归属/位置以及前序响应和独立 client 隔离；运行时结果不替代静态图验收。
