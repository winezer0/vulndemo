package fullvalueflow

func invoke_callback(callback func(string) string, value string) string {
	return callback(value)
}

func callback_parameter(callback_parameterInput string) string {
	selected := func(value string) string { return value }
	callback_parameterOut := invoke_callback(selected, callback_parameterInput)
	return callback_parameterOut
}

func callback_parameter_isolated(callback_parameter_isolatedInput string) string {
	selected := func(value string) string { return value }
	invoke_callback(selected, callback_parameter_isolatedInput)
	callback_parameter_isolatedOut := invoke_callback(selected, "safe")
	return callback_parameter_isolatedOut
}
