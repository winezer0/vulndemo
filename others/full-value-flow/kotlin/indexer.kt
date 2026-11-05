class IgnoringIndex {
    private var stored = "safe"
    operator fun get(index: Int): String = stored
    operator fun set(index: Int, value: String) { stored = value }
}
fun indexer_unconfirmed(indexer_unconfirmedInput: String): String {
    val holder = IgnoringIndex()
    holder[0] = indexer_unconfirmedInput
    val indexer_unconfirmedOut = holder[1]
    return indexer_unconfirmedOut
}
