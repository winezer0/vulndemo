package sample

type PointerArgumentItem struct{}

// consumePointerArguments accepts values whose pointer depths must survive indexing.
func consumePointerArguments(a, b any) {}

// forwardPointerArguments exercises address and dereference operations on a parameter.
func forwardPointerArguments(value *PointerArgumentItem) {
	consumePointerArguments(&value, *value)
}
