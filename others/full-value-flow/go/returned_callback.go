package fullvalueflow

// returned_identity returns one actual callable value.
func returned_identity() func(string) string { return func(value string) string { return value } }

// callback_returned invokes an actual returned callable.
func callback_returned(callback_returnedInput string) string {
	selected := returned_identity()
	callback_returnedOut := selected(callback_returnedInput)
	return callback_returnedOut
}

// callback_returned_isolation separates consecutive invocations.
func callback_returned_isolation(callback_returned_isolationInput string) string {
	selected := returned_identity()
	selected(callback_returned_isolationInput)
	callback_returned_isolationOut := selected("safe")
	return callback_returned_isolationOut
}
