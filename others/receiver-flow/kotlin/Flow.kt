interface Worker { fun sink(value: Int) }
class A : Worker { override fun sink(value: Int) {} }
class B : Worker { override fun sink(value: Int) {} }

fun conflict(flag: Boolean) {
    var p: Worker = A()
    if (flag) { p = A() } else { p = B() }
    p.sink(1)
}

fun same(flag: Boolean) {
    var p: Worker = A()
    if (flag) { p = B() } else { p = B() }
    p.sink(1)
}

fun wrapped() {
    val p = A()
    val result = (p).sink(1)
}
