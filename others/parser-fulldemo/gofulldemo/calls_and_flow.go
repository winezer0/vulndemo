package gofulldemo

import (
	"fmt"
	"time"
)

// FieldReadWriteDemo demonstrates field read/write operations
type FieldReadWriteDemo struct {
	Counter int
	Label   string
	Data    map[string]string
}

// FieldReadWrite performs field read/write operations
func (d *FieldReadWriteDemo) FieldReadWrite(value int) (int, string) {
	// Write operation: assignment
	d.Counter = value
	d.Label = "updated"

	// Compound assignment write
	d.Counter += 1

	// Increment write
	d.Counter++

	// Return value read
	return d.Counter, d.Label
}

// ReadFieldsInReturn reads fields in return values
func (d *FieldReadWriteDemo) ReadFieldsInReturn() (int, string) {
	return d.Counter, d.Label
}

// ReadFieldsInArg reads fields in arguments
func (d *FieldReadWriteDemo) ReadFieldsInArg() {
	PrintValues(d.Counter, d.Label)
}

// PrintValues demonstrates variadic argument printing
func PrintValues(vals ...any) {
	for _, v := range vals {
		fmt.Println(v)
	}
}

// CompositeLiteralDemo demonstrates composite literals
func CompositeLiteralDemo() User {
	u := User{
		ID:   1,
		Name: "Alice",
	}
	return u
}

// CompositeLiteralQualified demonstrates qualified composite literals
func CompositeLiteralQualified() {
	_ = time.Time{}
}

// ClosureDemo demonstrates closure capture
func ClosureDemo() func() int {
	count := 0
	return func() int {
		count++
		return count
	}
}

// GoroutineSpawn demonstrates goroutine spawning
func GoroutineSpawn() {
	go func() {
		fmt.Println("background work")
	}()
	go SimpleFunction()
}

// ChannelDemo demonstrates channel operations
func ChannelDemo() {
	ch := make(chan int, 1)
	ch2 := make(chan string)

	// Send
	ch <- 42

	// Receive
	val := <-ch
	_ = val

	// Standalone receive expression
	<-ch

	// Receive in select
	select {
	case v := <-ch:
		_ = v
	case ch2 <- "done":
	default:
	}
}

// TypeAssertionDemo demonstrates type assertions
func TypeAssertionDemo(v any) {
	if s, ok := v.(string); ok {
		_ = s
	}
	if n, ok := v.(User); ok {
		_ = n.ID
	}
}

// TypeSwitchDemo demonstrates type switch
func TypeSwitchDemo(v any) {
	switch x := v.(type) {
	case int:
		_ = x
	case string:
		_ = x
	default:
		_ = x
	}
}

// DeferClosure demonstrates defer with closure
func DeferClosure() {
	x := 10
	defer func() {
		fmt.Println(x)
	}()
}

// VarDeclBlock demonstrates variable declaration block
func VarDeclBlock() {
	var (
		a int    = 1
		b string = "two"
	)
	_, _ = a, b
}

// ShortVar demonstrates short variable declaration
func ShortVar() {
	msg := "hello"
	count := 5
	_, _ = msg, count
}
