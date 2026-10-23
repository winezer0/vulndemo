package review

val initialized = println("file initializer") // lexical:absent

fun baseline() {
    println("builtin") // lexical:absent
}

fun parameter(println: (String) -> Unit) {
    println("parameter") // lexical:bound
}

fun local() {
    fun println(value: String) {}
    println("local") // lexical:bound:function
}

fun deferred(println: (String) -> Unit) {
    val callback = {
        println("capture") // lexical:unknown
    }
}

class Derived : Parent() {
    fun caller() {
        println("inherited") // lexical:unknown
    }
}

fun Worker.extension() {
    println("receiver") // lexical:unknown
}

fun contextual(worker: Worker) {
    with(worker) {
        println("receiver lambda") // lexical:unknown
    }
}

fun packageCaller() {
    sourceRun("one")
    sourceRun("one", "two")
}
