package binding

// Caller invokes a parameter that shadows the predeclared function.
func Caller(len func(string) int) { len("value") }
