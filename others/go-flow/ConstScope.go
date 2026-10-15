package sample

const GlobalBase = "GLOBAL"

func constFirst() {
	const Alias = GlobalBase
	_ = Alias
}

func constSecond() {
	const GlobalBase = "LOCAL"
	const Alias = GlobalBase
	_ = Alias
}
