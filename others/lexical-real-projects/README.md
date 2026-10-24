# 六语言真实项目评估样本

这些是固定发布版本的真实开源库快照，未经修改；来源、commit、许可证及逐树 SHA256 见 [samples.json](samples.json)。原始许可证、NOTICE、源码内版权声明和二进制资源均保留。不代表大型业务项目、框架项目或安全漏洞样本的完整分布。

| 语言 | 项目/版本 | 语言或构建要求 | 跨文件观察入口 |
| --- | --- | --- | --- |
| Java | Apache Commons CLI 1.9.0 | Java 8；生产源码只依赖 JDK | Options/OptionGroup 调用 Option、Util |
| C# | Dapper 2.1.66 | SDK 9.0.101；目标 net461/netstandard2.0/net8.0 | Dapper 下 SqlMapper 的分文件实现；tests 为上游调用方 |
| Rust | anyhow 1.0.95 | edition 2018；最低 rustc 1.39；可选 backtrace | src 下 lib/error/context/kind 等模块 |
| PHP | PSR Log 3.0.2 | PHP >=8.0；生产代码无外部运行时包依赖 | AbstractLogger、LoggerTrait、LoggerInterface；接口派发不能当成唯一静态目标 |
| C | TinyCThread v1.1 | C11 threads API 子集；Win32/POSIX 平台分支 | source 实现及头文件，test/test.c 为外部调用方 |
| C++ | TinyXML2 10.0.0 | 以上游构建配置为准 | tinyxml2.cpp/.h，xmltest.cpp 为外部调用方 |

## 冻结及重建

首次获取使用根仓库 `script/fetch-lexical-real-projects.ps1`。脚本克隆固定 tag、验证实际 commit，并通过 `git archive` 导出；遵循上游 export-ignore，不包含 Git 数据库，不初始化嵌套仓库。已有目标目录时停止，避免覆盖样本。离线测试使用已保存快照，不执行网络获取。

本目录 `.gitattributes` 禁止快照换行转换。完整性测试：在 codeindex 执行 `go test ./testdata-indexer -run TestRealProjectSnapshots -count=1`。哈希算法见 manifest；任何源码、许可证或资源字节变化均需明确评审，不能为通过评估随意更新哈希。

TinyXML2 上游自身的 `.gitignore` 忽略了 archive 中实际存在的 `resources/out/readme.txt`。该原始文件必须用 `git add -f` 纳入版本管理；本轮已单独暂存，不修改上游 ignore 或快照字节。

## Java 首批真值

[java_truth.json](java_truth.json) 固定 36 个调用位置、源码表达式锚点、调用者及目标声明的文件/行/类型/名字。调用列由锚点经公共坐标函数转换，避免人工抄写列号错误；测试按目标节点完整 ID 比较，并检查 parser 原始调用位置与 resolver 输出。

抽样属于定向源码审阅，包含 OptionGroup 的源码目标调用、Options 的跨文件/重载/包内静态调用，以及 Option.getId。它不是随机抽样；同名遮蔽、外部依赖、复杂继承、反射和真实安全审计召回仍由其他 fixture 或后续样本覆盖。不能将本批准确率推广为项目总体准确率。

Java 验证在 JDK 21 下使用 `javac --release 8` 编译全部生产 Java 文件通过；`javap -c -l org.apache.commons.cli.Options` 的五个 `invokestatic Util.stripLeadingHyphens` 与真值行号一致。编译产物仅保留在临时目录，不混入样本。

## 既有样本盘点

- `Java_Vuln_Demo` 是漏洞教学项目集合；现有目录缺少可确认上游 commit 的 provenance，本轮不作为冻结真实项目基线。
- `java-reflection-interfaces` 只有一个 `Use.java`，属于语义回归，不属于跨文件真实项目。
- `dvwa` 保留 GPL 许可和真实 Web 项目结构，但现有目录还含生成索引、缺少当前快照 commit 证明；原质量/审计测试继续复用，本轮不将其旧数字作为新基线。
- `test_laravel` 是已有框架测试输入，尚未确认独立上游版本及完整性，作为既有回归使用。
- `csharp-flow`、`rust-flow`、`cpp-audit`、`easy/<language>` 和 `builtin-binding` 属于确定性测试/教学样例，继续用于最小回归与生命周期，不冒充真实项目样本。

六语言样本缺口已补齐；当前仅 Java 建立了独立调用真值和质量基线。其余五语言尚未完成质量评估。
