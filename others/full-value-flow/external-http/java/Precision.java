import java.net.URI;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

final class Precision {
    static byte[] streamReadBuffer(String streamBufferUrl) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(streamBufferUrl)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        InputStream streamBufferSource = response.body();
        byte[] buffer = new byte[4];
        streamBufferSource.read(buffer, 0, buffer.length);
        byte[] streamBufferOut = buffer;
        return streamBufferOut;
    }

    // synchronousString requires actual default client, send result and handler result identities.
    static String synchronousString(String stringInput) throws Exception {
        URI uri = URI.create(stringInput);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        String stringOut = response.body();
        return stringOut;
    }

    // synchronousBytes binds the returned array to the actual fixed byte-array handler.
    static byte[] synchronousBytes(String bytesInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(bytesInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        byte[] bytesOut = response.body();
        return bytesOut;
    }

    // synchronousStream exposes the body carrier without assuming any later read interval or success.
    static java.io.InputStream synchronousStream(String streamInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(streamInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        java.io.InputStream streamOut = response.body();
        return streamOut;
    }

    // streamRead consumes only an actual body stream from the supported synchronous response chain.
    static byte[] streamRead(String streamReadInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(streamReadInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        InputStream stream = response.body();
        byte[] streamReadOut = stream.readAllBytes();
        return streamReadOut;
    }

    // arbitraryStream keeps a possibly overridden InputStream implementation conservative.
    static byte[] arbitraryStream(InputStream unknownStreamInput) throws Exception {
        byte[] unknownStreamOut = unknownStreamInput.readAllBytes();
        return unknownStreamOut;
    }

    // closedStream preserves candidate content instead of treating close as sanitization or a successful read.
    static byte[] closedStream(String closedInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(closedInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        InputStream closedSource = response.body();
        closedSource.close();
        byte[] closedOut = closedSource.readAllBytes();
        return closedOut;
    }

    // localReplacing must not satisfy the no-argument ofString origin requirement.
    static String localReplacing(String localReplaceInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(localReplaceInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.replacing("fixed");
        HttpResponse<String> response = client.send(request, handler);
        String localReplaceOut = response.body();
        return localReplaceOut;
    }

    // customClient retains opaque semantics for a caller-supplied HttpClient implementation.
    static String customClient(String customInput, HttpClient client) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(customInput)).build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        String customOut = response.body();
        return customOut;
    }

    // reassignedClient must not reuse stale default-state evidence from the initial factory.
    static String reassignedClient(String clientReassignedInput, HttpClient custom) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(clientReassignedInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        client = custom;
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        String clientReassignedOut = response.body();
        return clientReassignedOut;
    }

    // unknownHandler must not infer content semantics from a generic BodyHandler type.
    static String unknownHandler(String handlerInput, HttpResponse.BodyHandler<String> handler) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(handlerInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, handler);
        String handlerOut = response.body();
        return handlerOut;
    }

    // reassignedHandler retains fallback when an earlier supported factory no longer identifies the value.
    static String reassignedHandler(String reassignedInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(reassignedInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        handler = HttpResponse.BodyHandlers.replacing("fixed");
        HttpResponse<String> response = client.send(request, handler);
        String reassignedOut = response.body();
        return reassignedOut;
    }

    // replacing requires a handler-aware model before response bytes can be claimed as the body.
    static String replacing(String replaceInput) throws Exception {
        URI uri = URI.create(replaceInput);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.replacing("fixed"));
        String replaceOut = response.body();
        return replaceOut;
    }

    // discarding retains its actual nullable Void result without fabricating received body content.
    static Object discarding(String discardInput) throws Exception {
        URI uri = URI.create(discardInput);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        Void discardOut = response.body();
        return discardOut;
    }

    // asynchronous preserves the actual future carrier and explicit join consumption.
    static String asynchronous(String asyncInput) {
        URI uri = URI.create(asyncInput);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = pending.join();
        String asyncOut = response.body();
        return asyncOut;
    }

    // canceledFuture cannot prove normal HTTP content after an actual cancellation request.
    static String canceledFuture(String canceledInput) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(canceledInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        pending.cancel(true);
        HttpResponse<String> response = pending.join();
        String canceledOut = response.body();
        return canceledOut;
    }

    // completedFuture must retain the caller-supplied response as a possible payload.
    static String completedFuture(String completedInput, HttpResponse<String> replacementInput) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(completedInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        pending.complete(replacementInput);
        HttpResponse<String> response = pending.join();
        String completedOut = response.body();
        return completedOut;
    }

    // asyncReplacement must not turn a known replacement handler into external response content.
    static String asyncReplacement(String asyncReplaceInput) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(asyncReplaceInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> pending = client.sendAsync(request, HttpResponse.BodyHandlers.replacing("fixed"));
        HttpResponse<String> response = pending.join();
        String asyncReplaceOut = response.body();
        return asyncReplaceOut;
    }

    // asynchronousStream preserves the future result and actual stream consumption boundaries.
    static byte[] asynchronousStream(String asyncStreamInput) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(asyncStreamInput)).build();
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<InputStream>> pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream());
        HttpResponse<InputStream> response = pending.join();
        InputStream stream = response.body();
        byte[] asyncStreamOut = stream.readAllBytes();
        return asyncStreamOut;
    }
}
