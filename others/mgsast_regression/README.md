# MGSAST 联合回归引用

`coverage_20261110_index.json` 固定历史 MGTV Session 中 18 个文件的全部 74 项失败义务。保存的状态是历史观察，不是语义标准答案。

原始响应留在显式选择的本地 Session，不复制 Provider 配置、请求头或大型模型会话到版本控制。源码复用 `../go-sec-code`。

运行 MGSAST Auditor adapter 的 `TestCoverageGapFrozenJointReplay`，将 `MGSAST_COVERAGE_REPLAY` 指向原始 Session 目录。该测试通过生产提交、检查点恢复和部分收尾回放原始工具参数，不调用模型。它使用当前 fixture 源码，验证提交和归档行为，不重现完整原始模型上下文，也不证明语义正确。

`obligation_calls/main.go` 用于真实 Go 解析和 CodeIndex 查询回归，覆盖归档导入别名、工厂返回值接收者、本地同名 Reader、普通 rows.Next、注释与字符串中的 time.Sleep 以及真实调用。
