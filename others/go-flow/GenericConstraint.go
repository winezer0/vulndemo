package sample

type GenericConstraint[T any] interface{}

func constrained[T GenericConstraint[T]](value T) T {
	return value
}
