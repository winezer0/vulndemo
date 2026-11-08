package sample

func Source() string { return "value" }

func Sink(string) {}

func Driver() {
	input := Source()
	complex := input + "!"
	Sink(complex)
}
