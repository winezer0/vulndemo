package sample

type MethodExpressionWorker struct{}
type MethodExpressionOther struct{}
type MethodExpressionValueWorker struct{}
type MethodExpressionValueOther struct{}

func (*MethodExpressionWorker) Run(string)     {}
func (*MethodExpressionOther) Run(string)      {}
func (MethodExpressionValueWorker) Run(string) {}
func (MethodExpressionValueOther) Run(string)  {}

func callMethodExpression(worker *MethodExpressionWorker, value string) {
	(*MethodExpressionWorker).Run(worker, value)
}

func callValueMethodExpression(value string) {
	MethodExpressionValueWorker.Run(value)
}
