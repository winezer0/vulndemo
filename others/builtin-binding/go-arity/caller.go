package binding

// Caller deliberately violates the source signature to exercise lexical binding.
func Caller() { len("value") }
