import pkg.Factory as F
fun caller() {
    val F: () -> Unit = { }
    F()
}
