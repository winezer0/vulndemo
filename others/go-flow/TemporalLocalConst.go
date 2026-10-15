package sample

import "go.temporal.io/sdk/workflow"

func dispatchWithLocalConst(ctx workflow.Context) {
	const LocalActivity = "local-activity"
	name := LocalActivity
	workflow.ExecuteActivity(ctx, name)
}
