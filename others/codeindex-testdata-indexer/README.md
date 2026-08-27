# 跨业务链路评估标注

`business_chains.yaml` 用于标注真实索引中应存在和禁止存在的调用边。

- `fixture`：相对于 `mgsast/testdata` 的项目目录。
- `edges`：预期的业务调用边。
- `forbidden`：不得解析或候选到的目标。
- `expected`：当前已人工核验的基线计数；发生变化时必须检查生成报告，不能直接改数字。

状态含义：

- `confirmed`：调用唯一解析到预期节点。
- `candidate`：预期节点只存在于候选集合。
- `unresolved`：保留了对应的未解析调用边。
- `wrong_binding`：同名调用被唯一解析到错误节点。
- `missing`：源节点、目标节点或调用事实缺失。

运行 `go test ./testdata-indexer -run TestIndexer_BusinessChainEvaluation -count=1 -v`，报告生成到忽略提交的 `testdata-indexer/reports/business-chain-evaluation.json`。
