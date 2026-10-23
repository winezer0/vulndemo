package lexicalfixture

func Initializer() {
	len := len("x") // lexical:absent
	_ = len
}

func Blocks() {
	{
		len := 0
		_ = len
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
	{
		len("x") // lexical:absent
	}
}

func Later() {
	len("x") // lexical:absent
	len := 0
	_ = len
}

func Parameter(len func(string) int) {
	len("x") // lexical:bound
}

func NamedResult() (len int) {
	len("x") // lexical:bound
	return
}

func Conditional() {
	if len := len("x"); len > 0 { // lexical:absent
		len("x") // lexical:bound
	} else {
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
}

func Loops() {
	for len := 0; len < 1; len++ {
		len("x") // lexical:bound
	}
	len("x")                              // lexical:absent
	for _, len := range []int{len("x")} { // lexical:absent
		len("x") // lexical:bound
	}
}

func Switches(value any, ch chan int) {
	switch len := 0; len {
	case 0:
		len("x") // lexical:bound
	default:
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
	switch len := value.(type) {
	case string:
		len("x") // lexical:bound
	default:
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
	select {
	case len := <-ch:
		len("x") // lexical:bound
	default:
		len("x") // lexical:absent
	}
	len("x") // lexical:absent
}

func Closures() {
	_ = func(len int) {
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
	{
		len := 0
		_ = func() {
			len("x") // lexical:bound
		}
		_ = len
	}
	len("x") // lexical:absent
}

func Declarations() {
	{
		const len = 0
		len("x") // lexical:bound
	}
	{
		type len int
		len("x") // lexical:bound
	}
	len("x") // lexical:absent
}

func SameLine() { len := len("x"); _ = len } // lexical:absent

func Signature(value [len("x")]byte, len int) { // lexical:absent
	_ = value
	_ = len
}

type Box[T any] struct{}

func (b Box[len]) Value(value [len("x")]byte) { // lexical:bound
	len("x") // lexical:bound
}

type Pair[T, U any] struct{}

func (b *Pair[T, len]) Pointer() {
	len("x") // lexical:bound
}
