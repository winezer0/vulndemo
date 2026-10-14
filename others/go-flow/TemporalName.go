package sample

import (
	"cmp"
	"os"
	"go.temporal.io/sdk/workflow"
)

func dispatch(ctx any) {
	name := cmp.Or(os.Getenv("ACTIVITY"), "Fallback")
	workflow.ExecuteActivity(ctx, name)
}
func dailyName() string { return "Daily" }
func dispatchFromHelper(ctx any) { name := dailyName(); workflow.ExecuteActivity(ctx, name) }
