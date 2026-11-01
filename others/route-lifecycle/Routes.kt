import io.ktor.server.routing.*
import io.ktor.server.application.*

fun configure() {
    routing {
        route("/private") {
            install(Auth)
            get("/kt") { }
        }
        route("/public") {
            get("/kt-open") { }
        }
    }
}
