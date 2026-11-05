import io.ktor.routing.*
fun serve() {}
fun Application.module() {
    get("/ktor", ::serve)
}
