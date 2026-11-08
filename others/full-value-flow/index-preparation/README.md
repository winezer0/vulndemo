# 索引准备与候选升级样例

从 MGSAST 旧 `graphprov/dbgen` 集成测试移入，源码保持原内容，由 `scanengine/pathmatch` 外部集成测试复用。

- `go`：基础值流与定向候选升级、resolver 和契约关系保真。
- `search`：真实 SQLite 规则扫描的升级与单次重试。
- `include_tests`：测试文件过滤、导入绑定保留和普通 import 节点不持久化。
- `go`、`typescript`、`python` 中的 `padding.txt`：基准测试追加到对应主源码，保持旧基准的文件内容和调用形态；不作为独立源码索引。

测试将样例复制到临时目录，数据库也在临时目录生成。以上样例用于解析、索引和查询验收，不执行其中的业务程序。
