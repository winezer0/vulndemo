import io.ktor.client.HttpClient as KtorClient
import io.ktor.client.request.get as fetchResponse
import io.ktor.client.statement.bodyAsText as consumeText

suspend fun ktorAliasBody(ktorAliasInput: String): String {
    val client = KtorClient()
    val response = client.fetchResponse(ktorAliasInput)
    val ktorAliasOut = response.consumeText()
    return ktorAliasOut
}
