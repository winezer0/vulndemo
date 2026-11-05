import okhttp3.OkHttpClient
import okhttp3.Request
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

fun okhttpBody(okhttpInput: String): String {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(okhttpInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val okhttpOut = body.string()
    return okhttpOut
}

suspend fun ktorBody(ktorInput: String): String {
    val client = HttpClient()
    val response = client.get(ktorInput)
    val ktorOut = response.bodyAsText()
    return ktorOut
}
