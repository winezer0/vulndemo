package gofulldemo

import (
	"errors"
	"fmt"
)

// SimpleFunction is a simple function
func SimpleFunction() {
	fmt.Println("hello")
}

// FunctionWithParams demonstrates a function with parameters
func FunctionWithParams(name string, age int) string {
	result := fmt.Sprintf("%s is %d years old", name, age)
	return result
}

// FunctionWithVariadic demonstrates a variadic function
func FunctionWithVariadic(items ...int) int {
	sum := 0
	for _, v := range items {
		sum += v
	}
	return sum
}

// FunctionWithMultiReturn demonstrates a multi-return function
func FunctionWithMultiReturn() (int, error) {
	return 42, nil
}

// FunctionReturningError demonstrates a function returning an error
func FunctionReturningError() error {
	return errors.New("something went wrong")
}

// FunctionWithNamedReturns demonstrates named return values
func FunctionWithNamedReturns() (result string, err error) {
	result = "ok"
	err = nil
	return
}

// FunctionWithDocComment demonstrates a function with doc comment
// Divide performs integer division of a by b.
// Returns an error when b is zero.
func Divide(a, b int) (int, error) {
	if b == 0 {
		return 0, errors.New("division by zero")
	}
	return a / b, nil
}

// Private function
func internalHelper() {
	_ = "internal"
}

// GenericFunction demonstrates a generic function
func GenericFunction[T any](items []T) T {
	var zero T
	if len(items) > 0 {
		return items[0]
	}
	return zero
}

// GenericFunctionWithConstraint demonstrates a constrained generic function
func GenericFunctionWithConstraint[T ~int | ~float64](a, b T) T {
	return a + b
}
