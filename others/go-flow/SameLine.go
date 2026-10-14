package sample

func sink(value int) {}
func run(a, b int) { sink(a); sink(a + b) }
type Alpha struct { Value int }
type Beta struct { Value int }
func first(item Alpha) int { return item.Value }; func second(item Beta) int { return item.Value }
type Pair struct { Left int; Right int }
func pair(left, right int) Pair { return Pair{Left: left, Right: right} }
