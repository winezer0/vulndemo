package sample

func Caller() {
 len("value")
 len := func(value string) int { return 0 }
 _ = len
}
