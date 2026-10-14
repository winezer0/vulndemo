package sample

import "reflect"

func invokeReflection(value reflect.Value, method string) {
	value.MethodByName(method).Call(nil)
}
