# 历史失败联合回归

`reasoning_quality_cases.json` 保存 read_body、SSTI、untar 和启动竞态的人工质量检查要点。它检查事实、理由与复现的一致性，不固定最终漏洞状态，也不进入生产判断。协议/引用回归通过不能替代这些语义检查。

`TestCurrentEvidenceAcrossProfiles` 使用真实 go-sec-code 索引，验证失效 handler 身份重新绑定、route ID/handler ID 转换、路由元数据投影以及 CodeIndex 执行路径进入 Profile 图上下文。该核心回归包含在 MGSAST 全量测试中，不调用 Provider。

`historical_joint_cases.json` 记录已检查的 104 份历史报告、75 个失败身份及原始文件摘要。仅纳入协议、执行、证据校验等工程失败；普通、有效的 `needs_review` 不算失败。

- Rule：57 个历史失败身份，旧身份按同一操作显式映射；加入已有语义反例后，共 54 个当前候选。
- MultiAgent：6 个历史候选，保留原扫描输入和 Profile。
- PromptScan：12 个历史 Review Item，使用生产转换和路由，不改造成 Rule 候选。
- 合计复验 72 项。确认、排除、有证据支持的待复核均可构成有效复核，不固定每项模型状态；还必须存在模型主复核的成功响应及成功提交。只补链、只查询或 Host 提前生成待复核不算模型复验完成。生产 WorkItem 合并的原候选可共享同一成功模型提交，映射另存 `model-review-validity.json`。协议通过不等于人工语义判定正确。

## 固定入口

在仓库根目录运行：

```powershell
rtk proxy powershell -NoProfile -File script/test-rule-joint.ps1 -OutputDirectory C:/ISEC/Deving/mgcodesec/gobuild/joint-run
```

追加 `-Live -TokenCeiling -1` 会在联合离线回归和 MGSAST 全量测试通过后，使用 MGTV 依次复验全部分组。`-1` 只能在获得不限 token 授权后使用；正数是每个 Session 的额度，不是所有批次累计额度。付费开关默认关闭。

每批最多 16 项、单 worker；失败批次保留原产物并继续其余固定案例，最终返回失败。任何付费调用前，先对所有分组校验扫描文件身份、源码摘要、候选 ID 和实际 CodeIndex 查询能力。能力预检使用候选起始行，原报告范围与证据继续完整传给 Verifier。历史扫描产物当前保存在本地 Session/gobuild 中，未随 fixture 入库；缺失时入口明确失败，需要从原审计归档恢复，不能伪造输入。

离线联合入口覆盖源码绑定、引用反馈、分类、操作身份、部分修复、待复核、资源触发、独立复核、查询恢复和静态报告描述。它复用已有生产边界测试；清单逐项校验只证明历史身份及源码一致，不冒充每项模型语义测试。真实复验结果另行记录。

## 已保存真实产物的无付费重验

MGSAST 模块内可运行 `go test ./auditflow/engineruntime/adapter -run=TestLiveJointArtifacts -count=1 -joint-live-artifacts=<联合产物绝对目录>`。沿用项目规定的 CGO 和缓存环境。此入口要求固定的 72 项全部存在，检查当前合同、输入/报告身份、成功模型提交和生产任务合并映射；只读取模型记录，不调用 Provider。通过后生成 `joint-model-review-acceptance.json`，原审计报告和失败日志不改写。

也可使用根目录脚本：`script/test-rule-joint.ps1 -OutputDirectory <新日志目录> -RevalidateArtifacts <联合产物目录>`。此模式不读取 Provider 配置，禁止与 `-Live` 同用。合同版本发生变化时，旧产物不能冒充新合同验收；应使用原版本核验历史产物。

## 输入归档与消耗对比

如需验证 Host 合并展开修复，可使用 `script/test-rule-joint.ps1 -OutputDirectory <新日志目录> -ReplayReports <原联合产物目录> -ReplayOutput <新派生产物目录>`。此入口通过生产 Journal 和 fan-out 重放已有主候选回执，再执行全部 72 项产物验收；不调用 Provider、不修改原报告、不改变模型结论。派生目录记录原报告摘要。它不代表原始执行命令成功，也不能为缺少有效模型复核的主候选补造结果。

- `script/export-rule-joint-inputs.ps1 -Destination <新目录>` 导出固定清单和三份原扫描输入，附 SHA-256 清单；不包含配置、密钥或模型会话。该归档是复跑输入，不替代完整历史诊断档案。匹配源码仍由 vulndemo 提供。
- 还原后用 `script/test-rule-joint.ps1 -OutputDirectory <新目录> -InputRoot <归档目录>`，执行前校验归档摘要。付费仍需显式 `-Live -TokenCeiling`。
- `-CandidateIDs <逗号分隔ID>` 可做定向复验，只允许固定清单中的身份。定向通过不能写成全部 72 项通过；全体复验应省略此参数。
- `script/analyze-rule-joint.ps1 -Artifacts <产物目录> -OutputFile <新JSON文件>` 从已保存事件统计调用、tokens、协议修复字段和修复消耗，不调用 Provider。仅统计 model_call 的 action_finished，避免重复累计 Provider usage。
- 新报告保留 Host 写入的 `model_review_status` 和具体降级证据；`model_review_complete` 单独表示全部候选获得有效模型复核。处理完成、模型有效复核、结论正确性是不同指标。旧报告没有该字段时不补造回执。

## 协议稳定性固定对比集

`protocol_repair_cases.json` 固定七次历史真实尝试中发生过协议修复、发布无效或缺失模型回执的 39 个身份（Rule 32、MultiAgent 5、Prompt 2）。同时保存 read_body 的两种源码转录反例，源码直接读取 go-sec-code 原文件。清单不规定模型应确认、排除还是待复核，也不将文本引用检查当成漏洞语义证明。

- `script/test-protocol-repair.ps1 -OutputDirectory <新目录> -InputRoot <冻结输入> -BaselineArtifacts <旧完整轮目录>`：复用完整离线联合及 MGSAST 全量测试。追加 `-Live -TokenCeiling -1` 才执行明确授权的 39 项 MGTV 复验；不会扩大到新的候选扫描。
- 原 72 项历史联合离线测试继续运行，源码 tuple 修复、已有 source ref、上下文 alias、独立复核回执与报告发布同时回归。
- `script/analyze-rule-joint.ps1` 可追加 `-CandidateIDs <固定ID列表>`。按生产 WorkItem 的合并映射计费，不把一个共享任务的 tokens 重复分摊到多个候选；缺失请求身份直接报错。
- `first_submission_stages` 按 task_id/role/attempt 区分初审和独立复核；`first_submission_successes` 统计该阶段第一次终端工具响应成功。网络失败且没有终端提交的阶段不进入此分母，仍由调用失败、未报告 usage 和有效复核数体现。首次提交成功不证明最终发布或自然语言分析正确。
- 对比优先使用完整原始轮相同 39 项，不能只拿筛选后的成功重试对比。工具分析不修改旧合同、不新增 Provider 调用；tokens、修复次数与有效结果一并报告，保留模型随机波动。
- `resumed_metrics_case.json` 与 `script/test-rule-joint-metrics.ps1` 验证恢复后调用编号复用及合并候选筛选：修复消耗按 task/role/attempt/action_id 关联，实际任务数读取生产 work 映射；不会把新尝试中的普通调用误算为旧尝试的修复调用。该回归不调用 Provider。
- `unfinished_requests` 单列已发出但无结束记录的请求，其 usage 未知，不计作零；`unreported_calls` 仍表示已有结束记录但没有 usage 的调用。总 tokens 只代表已报告消耗。
