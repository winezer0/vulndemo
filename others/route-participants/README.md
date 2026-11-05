# 路由参与者集成样例

供真实 parser → Memory/SQLite → QueryRoutes → 审计快照测试复用。
覆盖九种已有路由语言、28 个路径，框架范围与根仓库路由参与者实施计划一致。
Fastify 验证本地 hook 数组、重复项、动态展开与跨文件具名 hook 来源；Echo 验证固定 handler/middleware 参数；C# 验证 endpoint filter 候选及来源位置。
Express、Hono 及其他 Go 框架验证直接注册；其余框架验证已有 handler 候选贯通。tRPC 尚无参与者事实时必须显式未知。
位置 0 表示顺序未知，不能据此断言没有全局中间件；属性/数组顺序也不代表生命周期执行顺序。
C/C++ 当前没有对应的 Web 路由提取器，不用扩展名伪造支持。

生产链路验收入口为 `mgsast/auditflow/routeanalysis` 的 `TestCodeIndexParticipantPipeline`，统一引用本目录，不在代码模块复制样例。
