import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.InputStream

fun okhttpBytes(okhttpBytesInput: String): ByteArray {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(okhttpBytesInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val copied = body
    val okhttpBytesOut = copied.bytes()
    return okhttpBytesOut
}

fun okhttpCustom(client: OkHttpClient, okhttpCustomInput: String): String {
    val builder = Request.Builder()
    val configured = builder.url(okhttpCustomInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val okhttpCustomOut = body.string()
    return okhttpCustomOut
}

fun okhttpArbitrary(okhttpArbitraryInput: ResponseBody): String {
    val okhttpArbitraryOut = okhttpArbitraryInput.string()
    return okhttpArbitraryOut
}

fun okhttpClosed(okhttpClosedInput: String): String {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(okhttpClosedInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    body.close()
    val okhttpClosedOut = body.string()
    return okhttpClosedOut
}

fun okhttpStream(okhttpStreamInput: String): ByteArray {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(okhttpStreamInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val stream = body.byteStream()
    val okhttpStreamOut = stream.readAllBytes()
    return okhttpStreamOut
}

fun okhttpStreamBuffer(okhttpStreamBufferInput: String): ByteArray {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(okhttpStreamBufferInput)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val stream = body.byteStream()
    val buffer = ByteArray(4)
    stream.read(buffer, 0, 4)
    val okhttpStreamBufferOut = buffer
    return okhttpStreamBufferOut
}

fun okhttpPartial(okhttpPartialInput: String): ByteArray {
    val client = OkHttpClient()
    val request = Request.Builder().url(okhttpPartialInput).build()
    val response = client.newCall(request).execute()
    val body = response.body!!
    val okhttpPartialSource = body.byteStream()
    val okhttpPartialOut = okhttpPartialSource.readNBytes(5)
    return okhttpPartialOut
}

fun okhttpUnknownStream(okhttpUnknownStreamInput: InputStream): ByteArray {
    val okhttpUnknownStreamOut = okhttpUnknownStreamInput.readAllBytes()
    return okhttpUnknownStreamOut
}

fun okhttpStreamClosed(url: String): ByteArray {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(url)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val okhttpStreamClosedSource = body.byteStream()
    okhttpStreamClosedSource.close()
    val okhttpStreamClosedOut = okhttpStreamClosedSource.readAllBytes()
    return okhttpStreamClosedOut
}

fun okhttpBodyClosed(url: String): ByteArray {
    val client = OkHttpClient()
    val builder = Request.Builder()
    val configured = builder.url(url)
    val request = configured.build()
    val call = client.newCall(request)
    val response = call.execute()
    val body = response.body!!
    val okhttpBodyClosedSource = body.byteStream()
    body.close()
    val okhttpBodyClosedOut = okhttpBodyClosedSource.readAllBytes()
    return okhttpBodyClosedOut
}
