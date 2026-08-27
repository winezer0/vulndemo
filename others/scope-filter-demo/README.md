# CodeIndex 作用域筛选演示项目

这是一个可重复索引的多仓库、多项目测试工作区，用于演示 WebUI 的作用域筛选。

工作区包含两个仓库：

- `catalog-service|repositories/catalog`
  - 根项目：`.`
  - 子项目：`services/public`
- `web-console|repositories/web`
  - 根项目：`.`
  - 子项目：`apps/admin`

因此重新索引该目录后，WebUI 的 CodeIndex 范围列表应至少包含四个独立作用域。可分别验证：

- 按 `scope_id` 筛选；
- 按 `workspace_id + repo_prefix + project_id` 筛选；
- 按仓库 `RepoPrefix` 筛选；
- 按 `project_id` 筛选根项目或子项目；
- 按 `Go` 语言和包名筛选。

请在 WebUI 项目页选择本目录并点击“重新索引当前项目”。该目录不提交预生成的 SQLite 文件，避免测试数据和当前代码版本不一致。
