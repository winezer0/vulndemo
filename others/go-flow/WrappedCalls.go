package sample

// wrappedTarget accepts two generic values for callable identity regression.
func wrappedTarget[A, B any](first A, second B) {}

// invokeWrapped preserves callable identity through parentheses and instantiation.
func invokeWrapped(input string) {
	(wrappedTarget[string, string])(input, input)
}
