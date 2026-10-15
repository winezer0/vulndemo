package sample

type LocalBox[T any] struct {
	Value T
}

func genericLocals() {
	var value LocalBox[string]
	var pointer *LocalBox[string]
	_ = value
	_ = pointer
}
