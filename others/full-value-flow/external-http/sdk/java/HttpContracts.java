import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

public final class HttpContracts {
    // check rejects a mismatched SDK contract without external test dependencies.
    private static void check(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }

    private static boolean isBodyPrefix(byte[] value) {
        int length = 0;
        while (length < value.length && value[length] != 0) length++;
        return length > 0 && "fixed-java-body".startsWith(new String(value, 0, length, StandardCharsets.UTF_8));
    }

    // main exercises real loopback requests and body handler boundaries.
    public static void main(String[] args) throws Exception {
        byte[] payload = "fixed-java-body".getBytes(StandardCharsets.UTF_8);
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/fixed", exchange -> {
            exchange.sendResponseHeaders(201, payload.length);
            try (var output = exchange.getResponseBody()) { output.write(payload); }
            exchange.close();
        });
        server.start();
        try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
            URI uri = URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/fixed");
            HttpRequest request = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(5)).build();
            var text = client.send(request, HttpResponse.BodyHandlers.ofString());
            check(text.statusCode() == 201, "status mismatch");
            check(text.body().equals("fixed-java-body"), "text mismatch");
            var binary = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            check(Arrays.equals(binary.body(), payload), "binary mismatch");
            var streamed = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream input = streamed.body()) {
                check(new String(input.readNBytes(5), StandardCharsets.UTF_8).equals("fixed"), "partial read mismatch");
            }
            var fixtureStream = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream input = fixtureStream.body()) {
                check(isBodyPrefix(StreamBuffer.consume(input)), "fixture buffer mismatch");
            }
            check(isBodyPrefix(Precision.streamReadBuffer(uri.toString())), "HTTP buffer chain mismatch");
            var aliasStream = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream input = aliasStream.body()) {
                byte[] shared = new byte[4];
                check(isBodyPrefix(StreamBuffer.consumeInto(input, shared, shared)), "fixture alias mismatch");
            }
            var asynchronous = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            check(asynchronous.body().equals(text.body()), "async mismatch");
            var replaced = client.send(request, HttpResponse.BodyHandlers.replacing("local-replacement"));
            check(replaced.body().equals("local-replacement"), "replacement mismatch");
            var discarded = client.send(request, HttpResponse.BodyHandlers.discarding());
            check(discarded.body() == null, "discard mismatch");
            try (HttpClient defaultClient = HttpClient.newHttpClient()) {
                var fullStream = defaultClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
                try (InputStream input = fullStream.body()) {
                    check(Arrays.equals(input.readAllBytes(), payload), "default stream full read mismatch");
                    check(input.readAllBytes().length == 0, "stream EOF mismatch");
                }
            }
            CompletableFuture<HttpResponse<String>> injected = new CompletableFuture<>();
            check(injected.complete(replaced) && injected.join().body().equals("local-replacement"), "manual future payload mismatch");
            CompletableFuture<HttpResponse<String>> canceled = new CompletableFuture<>();
            check(canceled.cancel(true), "future cancellation mismatch");
            boolean canceledJoin = false;
            try { canceled.join(); } catch (CancellationException expected) { canceledJoin = true; }
            check(canceledJoin, "canceled future join produced a payload");
            System.out.println("runtime=" + Runtime.version() + "; cases=15; status=passed");
        } finally {
            server.stop(0);
        }
    }
}
