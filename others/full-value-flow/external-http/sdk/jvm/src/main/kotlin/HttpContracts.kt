import com.sun.net.httpserver.HttpServer
import io.ktor.client.HttpClient
import io.ktor.client.HttpClient as KtorClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.get as fetchResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.bodyAsText as consumeText
import java.net.InetSocketAddress
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils

// main executes pinned Java and Kotlin HTTP clients against a fixed loopback service.
fun main() {
    val payload = "fixed-jvm-body".toByteArray(Charsets.UTF_8)
    val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
    server.createContext("/") { exchange ->
        val status = if (exchange.requestURI.path == "/error") 500 else 201
        exchange.sendResponseHeaders(status, payload.size.toLong())
        exchange.responseBody.use { it.write(payload) }
        exchange.close()
    }
    server.start()
    try {
        val base = "http://127.0.0.1:${server.address.port}"
        checkOkHttp(base, payload)
        checkApache(base)
        checkKtor(base)
        println("jdk=${Runtime.version()}; okhttp=4.12.0; apache=5.4.1; ktor=3.0.1; cases=26; status=passed")
    } finally {
        server.stop(0)
    }
}

// checkOkHttp verifies actual response, bytes and partial stream carriers.
fun checkOkHttp(base: String, payload: ByteArray) {
    val bufferPrefix = String(okhttpStreamBuffer("$base/fixed"), Charsets.UTF_8).trimEnd('\u0000')
    check(bufferPrefix.isNotEmpty() && "fixed-jvm-body".startsWith(bufferPrefix))
    check(okhttpStream("$base/fixed").contentEquals(payload))
    check(String(okhttpPartial("$base/fixed"), Charsets.UTF_8) == "fixed")
    check(runCatching { okhttpClosed("$base/fixed") }.isFailure)
    check(runCatching { okhttpStreamClosed("$base/fixed") }.isFailure)
    check(runCatching { okhttpBodyClosed("$base/fixed") }.isFailure)
    val client = OkHttpClient()
    client.newCall(Request.Builder().url("$base/fixed").build()).execute().use { response ->
        check(response.code == 201)
        check(response.body!!.string() == "fixed-jvm-body")
    }
    client.newCall(Request.Builder().url("$base/fixed").build()).execute().use { response ->
        check(response.body!!.bytes().contentEquals(payload))
    }
    client.newCall(Request.Builder().url("$base/fixed").build()).execute().use { response ->
        response.body!!.byteStream().use { check(String(it.readNBytes(5), Charsets.UTF_8) == "fixed") }
    }
    client.newCall(Request.Builder().url("$base/fixed").build()).execute().use { response ->
        response.body!!.byteStream().use {
            val prefix = String(kotlinLocalBuffer(it), Charsets.UTF_8).trimEnd('\u0000')
            check(prefix.isNotEmpty() && "fixed-jvm-body".startsWith(prefix))
        }
    }
    client.newCall(Request.Builder().url("$base/fixed").build()).execute().use { response ->
        response.body!!.byteStream().use {
            val buffer = ByteArray(4)
            val prefix = String(kotlinReadBuffer(it, buffer, buffer), Charsets.UTF_8).trimEnd('\u0000')
            check(prefix.isNotEmpty() && "fixed-jvm-body".startsWith(prefix))
        }
    }
    client.connectionPool.evictAll()
    client.dispatcher.executorService.shutdown()
}

// checkApache keeps entity content distinct from status and HTTP failure handling.
fun checkApache(base: String) {
    HttpClients.createDefault().use { client ->
        client.executeOpen(null, HttpGet("$base/fixed"), null).use { response ->
            check(EntityUtils.toString(response.entity) == "fixed-jvm-body")
        }
        client.execute(HttpGet("$base/fixed")).use { response ->
            check(response.code == 201)
            check(EntityUtils.toString(response.entity) == "fixed-jvm-body")
        }
        client.execute(HttpGet("$base/error")).use { response ->
            check(response.code == 500)
            check(EntityUtils.toString(response.entity) == "fixed-jvm-body")
        }
    }
}

// checkKtor executes actual suspending calls and closes the CIO engine.
fun checkKtor(base: String) = runBlocking {
    check(pluginKtor("$base/fixed") == "fixed-jvm-body")
    KtorClient().use { client ->
        val response = client.fetchResponse("$base/fixed")
        check(response.status.value == 201)
        check(response.consumeText() == "fixed-jvm-body")
    }
    HttpClient().use { client ->
        val response = client.get("$base/fixed")
        check(response.status.value == 201)
        check(response.bodyAsText() == "fixed-jvm-body")
    }
    HttpClient(CIO).use { client ->
        val response = client.get("$base/fixed")
        check(response.status.value == 201)
        check(response.bodyAsText() == "fixed-jvm-body")
        val failure = client.get("$base/error")
        check(failure.status.value == 500)
        check(failure.bodyAsText() == "fixed-jvm-body")
    }
}
