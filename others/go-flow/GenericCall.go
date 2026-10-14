package sample

func genericValue[T any](value T) {}

func invokeGeneric(input string) {
	genericValue[string](input)
}
