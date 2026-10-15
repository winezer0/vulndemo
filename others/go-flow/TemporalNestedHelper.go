package sample

import "go.temporal.io/sdk/workflow"

func nestedActivityName() string {
	nested := func() string { return "wrong" }
	_ = nested
	return "NestedDaily"
}

func dispatchNestedActivity(ctx any) {
	name := nestedActivityName()
	workflow.ExecuteActivity(ctx, name)
}
