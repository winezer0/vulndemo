package sample

type Alpha struct{}
type Beta struct{}

func sink(value int) {}

func first(a int) { _ = Alpha{}; sink(a) } func second(b int) { _ = Beta{}; sink(b) }
