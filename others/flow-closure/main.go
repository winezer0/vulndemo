package flowclosure

// Source supplies a distinguishable producer in the shared index fixture.
func Source() string { return "external" }

// Sink represents the selected operation; the fixture is not an exploit.
func Sink(value string) {}

// Driver requires FullValueFlow to connect its concatenation to the sink.
func Driver() {
	input := Source()
	complex := input + "!"
	Sink(complex)
}

// Unrelated provides a constant-only sibling call for isolation checks.
func Unrelated() {
	Sink("constant")
}
