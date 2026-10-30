fun ktSink(value: Any) {}

fun ktTarget(first: String, second: String) {
    ktSink(first)
    ktSink(second)
}

fun ktCaller(input: String) {
    val value = input
    ktTarget("safe", value)
}

fun ktNamedCaller(input: String) {
    val value = input
    ktTarget(second = value, first = "safe")
}

fun ktRest(first: String, vararg values: String) {
    ktSink(values)
}

fun ktRestCaller(input: String) {
    val value = input
    ktRest("safe", "safe", value)
}

fun ktSpreadCaller(input: Array<String>) {
    val value = input
    ktRest("safe", *value)
}

fun ktSource(): String { return "input" }

fun ktReturnTarget(): String {
    val value = ktSource()
    return value
}

fun ktWrappedReturnTarget(): String {
    val value = ktSource()
    return ((value))
}

fun ktWrappedReturnCaller() {
    val second = ktWrappedReturnTarget()
    ktSink(second)
}

fun ktReturnCaller() {
    val second = ktReturnTarget()
    ktSink(second)
}

fun ktParenthesizedReturnCaller() {
    val second = ((ktReturnTarget()))
    ktSink(second)
}
