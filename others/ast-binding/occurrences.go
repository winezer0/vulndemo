package astbinding

// Source supplies an input for syntax contract tests, without asserting a vulnerability.
func Source() string { return "input" }

// Sink consumes a value for syntax contract tests.
func Sink(value string) {}

// ArgumentTarget exposes two distinct formal slots.
func ArgumentTarget(first, second string) {
	Sink(second)
	Sink(first)
}

// ArgumentCaller passes its value in the second actual slot.
func ArgumentCaller(input string) {
	value := input
	ArgumentTarget("safe", value)
}

// ReturnTarget exposes two distinct return slots.
func ReturnTarget() (string, string) {
	value := Source()
	return "safe", value
}

// ReturnCaller receives both slots and consumes them independently.
func ReturnCaller() {
	first, second := ReturnTarget()
	Sink(second)
	Sink(first)
}

// SingleReturnTarget supplies one return slot for transparent expression tests.
func SingleReturnTarget() string {
	value := Source()
	return value
}

// ParenthesizedReturnCaller retains the exact inner call occurrence.
func ParenthesizedReturnCaller() {
	second := (SingleReturnTarget())
	Sink(second)
}

// CombinedReturnCaller transforms the returned value before receiving it.
func CombinedReturnCaller() {
	second := (SingleReturnTarget() + "suffix")
	Sink(second)
}

// NamedReturnTarget returns the second named slot without an explicit expression.
func NamedReturnTarget() (first, value string) {
	value = Source()
	first = "safe"
	return
}

// NamedReturnCaller consumes the named result independently from the first slot.
func NamedReturnCaller() {
	first, second := NamedReturnTarget()
	Sink(second)
	Sink(first)
}

// NamedIgnoredReturnCaller preserves slot identity after discarding the first result.
func NamedIgnoredReturnCaller() {
	_, second := NamedReturnTarget()
	Sink(second)
}

// NamedSingleReturnTarget keeps an unrelated nested bare return in a separate callable.
func NamedSingleReturnTarget() (value string) {
	value = Source()
	inner := func() { return }
	inner()
	return
}

// NamedSingleReturnCaller receives the outer callable's named slot.
func NamedSingleReturnCaller() {
	second := NamedSingleReturnTarget()
	Sink(second)
}

// WrappedReturnTarget preserves transparent syntax in multiple return slots.
func WrappedReturnTarget() (string, string) {
	value := Source()
	return ("safe"), /* transparent */ (value)
}

// WrappedReturnCaller receives the exact second result without changing its slot.
func WrappedReturnCaller() {
	first, second := WrappedReturnTarget()
	Sink(first)
	Sink(second)
}

// IgnoredReturn preserves the second slot while discarding the first.
func IgnoredReturn() {
	_, second := ReturnTarget()
	Sink(second)
}

// SameLineReturn preserves two independent calls on one source line.
func SameLineReturn() {
	first, second := ReturnTarget()
	third, fourth := ReturnTarget()
	Sink(fourth)
	Sink(third)
	_, _, _ = first, second, third
}

// UnrelatedReceiver keeps an unrelated assignment on the same line as a discarded call.
func UnrelatedReceiver() {
	value := Source()
	ReturnTarget()
	Sink(value)
}

// TextImpersonation contains executable and non-executable lookalikes.
func TextImpersonation() {
	value := "safe" // Source()
	quoted := `Source()`
	SourceIdentifier := "safe"
	Sink(value)
	Sink(quoted)
	Sink(SourceIdentifier)
}

// DuplicateExpression preserves both identical occurrences inside one operation.
func DuplicateExpression() {
	value := Source() + Source()
	Sink(value)
}

// NestedCallable separates an inner callable from its enclosing function.
func NestedCallable() {
	value := "outer"
	inner := func() {
		value := Source()
		Sink(value)
	}
	inner()
	Sink(value)
}
