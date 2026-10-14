import io.ktor.routing.*

@RestController
class Api {
    @GetMapping(path = "/users")
    fun users(): String = "ok"

    @PostMapping(DYNAMIC_PATH)
    fun dynamic(): String = "ok"

    @RequestMapping(path = "/explicit", method = RequestMethod.POST)
    fun explicit(): String = "ok"

    @RequestMapping(path = "/unknown", method = DYNAMIC_METHOD)
    fun unknown(): String = "ok"
}

class Box<T : Number>(val value: T)

fun variadic(required: Int, vararg rest: String) {}
fun consume(items: Map<String, Int>, flag: Boolean = true) {}
fun wrap(f: (Int) -> String): (Int) -> String = f

@Entity
@Table(name = TABLE_NAME)
class DynamicTable

@Service
class Consumer {
    @Autowired lateinit var repo: Repo
}

fun Application.module(id: String) {
    get("/ktor/users") {}
    get("/ktor/users/$id") {}
}

class Mutable {
    var x: Int = 0
    fun update() { this.x = 1 }
}

@RestController
class Multi {
    @RequestMapping(path = "/items", method = [RequestMethod.GET, RequestMethod.POST])
    fun items(): String = "ok"
}

object Counter {
    var count: Int = 0
    fun advance() { this.count = 1 }
}

class FlowBox {
    var x: Int = 0
    fun move(v: Int): Int {
        this.x = v
        val saved = x
        return saved
    }
}

class FirstOwner {
    companion object {
        fun create(): FirstOwner = FirstOwner()
    }
}

class SecondOwner {
    companion object {
        fun create(): SecondOwner = SecondOwner()
    }
}
