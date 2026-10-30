package astbinding

// VariadicTarget consumes an explicit variadic declaration slot.
func VariadicTarget(first string, values ...string) {
	Sink(values[0])
}

// VariadicCaller selects an ordinary actual after the first variadic argument.
func VariadicCaller(input string) {
	value := input
	VariadicTarget("safe", "safe", value)
}

// VariadicSpread leaves the number of actual argument slots unknown.
func VariadicSpread(input []string) {
	value := input
	VariadicTarget("safe", value...)
}

// Receiver supplies a method whose receiver convention is not an ordinary parameter.
type Receiver struct{ Value string }

// Method consumes its first explicit formal parameter.
func (receiver Receiver) Method(value string) {
	Sink(value)
	Sink(receiver.Value)
}

// ReceiverCaller exercises a bound receiver call.
func ReceiverCaller(input string) {
	value := input
	receiver := Receiver{}
	receiver.Method(value)
}

// UnboundReceiverCaller exercises an explicit receiver in a method expression.
func UnboundReceiverCaller(input string) {
	value := input
	Receiver.Method(Receiver{}, value)
}

// ReceiverValueCaller passes the bound object to the method receiver declaration.
func ReceiverValueCaller(input Receiver) {
	value := input
	value.Method("safe")
}

// ExplicitReceiverValueCaller supplies the method expression receiver as its first actual.
func ExplicitReceiverValueCaller(input Receiver) {
	value := input
	Receiver.Method(value, "safe")
}

// ShadowReceiverCaller keeps a value named like its type in the bound convention.
func ShadowReceiverCaller(input string) {
	value := input
	Receiver := Receiver{}
	Receiver.Method(value)
}
