package fullvalueflow

// captured_factory returns a callable over its actual creator argument.
func captured_factory(value string) func() string { return func() string { return value } }

// callback_capture invokes the returned captured environment.
func callback_capture(callback_captureInput string) string {
	selected := captured_factory(callback_captureInput)
	callback_captureOut := selected()
	return callback_captureOut
}

// callback_capture_isolation keeps separate factory environments apart.
func callback_capture_isolation(callback_capture_isolationInput string) string {
	first := captured_factory(callback_capture_isolationInput)
	second := captured_factory("safe")
	first()
	callback_capture_isolationOut := second()
	return callback_capture_isolationOut
}
