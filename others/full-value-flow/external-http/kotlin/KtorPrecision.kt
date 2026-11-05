import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

// pluginKtor keeps an explicitly configured client distinct from the default client.
suspend fun pluginKtor(ktorPluginInput: String): String {
    val client = HttpClient { install(HttpTimeout) { requestTimeoutMillis = 5000 } }
    val response = client.get(ktorPluginInput)
    val ktorPluginOut = response.bodyAsText()
    client.close()
    return ktorPluginOut
}

suspend fun customKtor(client: HttpClient, ktorCustomInput: String): String {
    val response = client.get(ktorCustomInput)
    val ktorCustomOut = response.bodyAsText()
    return ktorCustomOut
}

suspend fun closedKtor(ktorClosedInput: String): String {
    val client = HttpClient()
    val response = client.get(ktorClosedInput)
    client.close()
    val ktorClosedOut = response.bodyAsText()
    return ktorClosedOut
}
