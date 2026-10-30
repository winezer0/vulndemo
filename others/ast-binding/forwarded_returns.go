package astbinding

// ForwardedPair supplies independent results for forwarding slot regressions.
func ForwardedPair() (string, string) {
	value := Source()
	return "safe", value
}

// ForwardedOnce forwards both results without receiving them into local identifiers.
func ForwardedOnce() (string, string) {
	return ForwardedPair()
}

// ForwardedTwice preserves both result positions through another callable.
func ForwardedTwice() (first, second string) {
	return (ForwardedOnce())
}

// ForwardedCaller receives the final independent result positions.
func ForwardedCaller() {
	first, second := ForwardedTwice()
	Sink(second)
	Sink(first)
}

// ForwardedIgnoredCaller discards only the first result.
func ForwardedIgnoredCaller() {
	_, second := ForwardedTwice()
	Sink(second)
}

// ForwardedSameLineCaller keeps repeated call sites distinct on one source line.
func ForwardedSameLineCaller() {
	firstA, secondA := ForwardedTwice(); firstB, secondB := ForwardedTwice()
	Sink(secondB)
	Sink(secondA)
}
