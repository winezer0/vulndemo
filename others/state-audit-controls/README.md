# 状态审计独立对照

这些样例不作为生产规则特例。扫描只生成候选，安全结论由验证阶段判断。

| 样例 | 扫描预期 | 语义验收依据 |
| --- | --- | --- |
| shared.py | 命中 | 当目录项可被攻击者控制时，固定名称且缺少独占创建存在链接替换风险；目录可写能力未证实时须明确缺口。 |
| exclusive.py | 命中 | O_EXCL 与 O_CREAT 的原子独占创建是当前文件创建竞争机制的反证；不能因同名 API 就确认该机制。目录祖先替换等不同攻击需另行分析。 |
| unrelated.py | 不命中 | 无文件创建操作。 |

Go 对照复用 `../rule-audit-obligations/operations.go`、`controls.go`、`unrelated.go`。无 source 的状态规则应保留正反两类调用信号；配置保护不能被扫描器提前丢弃。

Python 此处使用测试自有 call/state 规则验证真实索引及扫描能力，不代表已新增 Python 生产文件竞争规则。未进行这些对照的真实模型验证前，不得宣称跨语言语义审计通过或计算正式精确率。

## 私有目录独占创建模型对照

| 样例 | 扫描预期 | 语义范围 |
| --- | --- | --- |
| private.go | 命中 | MkdirTemp + O_CREATE/O_EXCL，最终目录项创建竞争的保护对照。 |
| private.py | 命中 | TemporaryDirectory + O_CREAT/O_EXCL，同机制Python保护对照。 |

a6api deepseek-v4.1-flash-1017同冻结版本真实验证通过，原始记录位于mgsast的rule-rejection-controls-feedback-a6api-20261029目录。前文“仅扫描、尚未真实验证”适用于先前三份Python样例；不能将本次两个保护对照推广为全部样例或语言均已验证。

拒绝仅针对最终目录项预置/替换机制；私有目录不是隔离所有同UID或特权进程的保证，随机名称也不能替代原子独占创建。未修改生产规则，不计算正式精确率。
