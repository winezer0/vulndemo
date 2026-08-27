# go-sec-code audit truth

`truth.json` is the versioned root-cause benchmark for the frozen go-sec-code source snapshot. It contains 19 known vulnerability roots and explicit exclusions for safe fixtures and known non-security findings.

Evaluate a saved real Provider report:

```powershell
go run ./cmd/mgsast-quality `
  -truth audit_truth/go-sec-code-truth.json `
  -candidates testdata/go-sec-code/promptscan-two-level-acceptance-v2.json `
  -json-out testdata/go-sec-code/audit_truth/current.json `
  -markdown-out testdata/go-sec-code/audit_truth/current.md `
  -baseline testdata/go-sec-code/audit_truth/promptscan-two-level-baseline.json
```

The JSON output separates quality deltas (recall, precision, false positives, duplicates) from execution deltas (model calls, tool calls, tokens, duration). Compare only reports produced from the same `source_snapshot_digest`; Provider variation is measured as execution variance and must not be treated as a code regression.
