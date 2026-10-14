package contracts.kotlin

class Box(var value: Int)

fun sink(value: Int) {}

fun run(first: Box, second: Box) { val action = fun() { sink(first.value) }; val action2 = fun() { sink(second.value) } }
