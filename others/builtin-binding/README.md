# 内置名称绑定回归

- `kotlin-local`：跨文件顶层 `run` 应绑定本地声明。
- `go-local`：跨文件自定义 `len` 应覆盖预声明名称，不得被低价值内置调用过滤。
- `kotlin-unknown`：无法确认成员方法时保留未解析，不能绑定全局内置 `run`。
- `kotlin-member`：真实成员 `Alpha.let` 优先于目录中的通配扩展方法。

供 CodeIndex 的 Memory/SQLite 全量及增量回归使用，不依赖外部 semantic Provider。
