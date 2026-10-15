package construction
class Widget(val size: Int)
fun create(size: Int): Widget { return Widget(size) }
fun factory(size: Int): Widget { return create(size) }
