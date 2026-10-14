package sample

func consumeNested(string) {}

func runNestedClosure(shared string) {
	outer := func() {
		inner := func() { consumeNested(shared) }
		inner()
	}
	outer()
}
