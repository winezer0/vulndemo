package binding

// len shadows the predeclared function even when a call has invalid arguments.
func len(a string, b string) int { return 0 }
