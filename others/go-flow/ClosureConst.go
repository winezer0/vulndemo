package sample

func consumeLabel(string) {}

func runClosureConst() {
	fn := func() {
		const label = "ready"
		consumeLabel(label)
	}
	fn()
}
