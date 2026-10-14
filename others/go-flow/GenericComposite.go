package sample

type GenericLiteralBox[T any] struct{}

func (GenericLiteralBox[T]) Run() {}

func genericLiteralCall() {
	box := GenericLiteralBox[string]{}
	box.Run()
}
