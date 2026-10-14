package sample

type AssertedParent struct{}
type AssertedChild struct{}

func (*AssertedParent) Next() *AssertedChild { return &AssertedChild{} }
func (*AssertedChild) Run() {}
func useAssertedChain(value any) { value.(*AssertedParent).Next().Run() }
