package fullvalueflow

// callback_capture_local invokes a closure in the scope that owns its captured value.
func callback_capture_local(callback_capture_localInput string) string {
	current := callback_capture_localInput
	selected := func() string { return current }
	callback_capture_localOut := selected()
	return callback_capture_localOut
}

// callback_capture_local_isolation keeps an unrelated captured value out of the result.
func callback_capture_local_isolation(callback_capture_local_isolationInput string) string {
	current := callback_capture_local_isolationInput
	first := func() string { return current }
	safe := "safe"
	second := func() string { return safe }
	_ = first()
	callback_capture_local_isolationOut := second()
	return callback_capture_local_isolationOut
}

// captured_mutation_factory retains a mutable lexical cell across callable creation.
func captured_mutation_factory(value string) func() string {
	current := "safe"
	selected := func() string { return current }
	current = value
	return selected
}

// callback_capture_write observes a write made after callable creation.
func callback_capture_write(callback_capture_writeInput string) string {
	selected := captured_mutation_factory(callback_capture_writeInput)
	callback_capture_writeOut := selected()
	return callback_capture_writeOut
}
