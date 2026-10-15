package sample

import "go.temporal.io/sdk/workflow"

// scopedTemporalName ignores unrelated closure-local declarations.
func scopedTemporalName(ctx workflow.Context) {
	name := "Daily\x2fTask"
	_ = func() { name := "Wrong"; _ = name }
	workflow.ExecuteActivity(ctx, name)
}
