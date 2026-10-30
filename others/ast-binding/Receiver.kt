class ReceiverKotlin {
    fun receiverTarget() {
        receiverSink(this)
    }
}
fun receiverCaller(input: ReceiverKotlin) {
    val value = input
    value.receiverTarget()
}
