import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ThirdPartyContracts {
    private static final byte[] PAYLOAD = "fixed-third-party".getBytes(StandardCharsets.UTF_8);

    // main verifies pinned SDK carriers and records post-close behavior without assuming buffered state.
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            exchange.sendResponseHeaders(200, PAYLOAD.length);
            exchange.getResponseBody().write(PAYLOAD);
            exchange.close();
        });
        server.start();
        try {
            String base = "http://127.0.0.1:" + server.getAddress().getPort();
            checkOkHttp(base);
            checkApache(base);
            System.out.println("okhttp=4.12.0; apache=5.4.1; cases=9; status=passed");
        } finally {
            server.stop(0);
        }
    }

    // checkOkHttp verifies content, independent requests, closure and interceptor replacement.
    private static void checkOkHttp(String base) throws Exception {
        OkHttpClient client = new OkHttpClient();
        try {
            try (Response response = client.newCall(new Request.Builder().url(base).build()).execute()) {
                check(response.body().string().equals("fixed-third-party"));
            }
            try (Response response = client.newCall(new Request.Builder().url(base).build()).execute()) {
                check(Arrays.equals(response.body().bytes(), PAYLOAD));
            }
            try (Response response = client.newCall(new Request.Builder().url(base).build()).execute()) {
                check(new String(response.body().byteStream().readNBytes(5), StandardCharsets.UTF_8).equals("fixed"));
            }
            Response closed = client.newCall(new Request.Builder().url(base).build()).execute();
            ResponseBody body = closed.body();
            closed.close();
            boolean rejected = false;
            try {
                body.string();
            } catch (IOException | IllegalStateException expected) {
                rejected = true;
            }
            check(rejected);
            OkHttpClient custom = client.newBuilder().addInterceptor(chain -> new Response.Builder()
                .request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("local")
                .body(ResponseBody.create("replacement", MediaType.get("text/plain"))).build()).build();
            try (Response response = custom.newCall(new Request.Builder().url(base).build()).execute()) {
                check(response.body().string().equals("replacement"));
            }
        } finally {
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
    }

    // checkApache verifies actual entity consumers and records the effect of closing the response.
    private static void checkApache(String base) throws Exception {
        try (var client = HttpClients.createDefault()) {
            try (var response = client.execute(new HttpGet(base))) {
                check(response.getCode() == 200);
                check(EntityUtils.toString(response.getEntity()).equals("fixed-third-party"));
            }
            try (var response = client.execute(new HttpGet(base))) {
                check(Arrays.equals(EntityUtils.toByteArray(response.getEntity()), PAYLOAD));
            }
            var response = client.execute(new HttpGet(base));
            var entity = response.getEntity();
            response.close();
            check(entity != null);
            try {
                System.out.println("apache_closed_entity_read=" + EntityUtils.toString(entity));
            } catch (IOException expected) {
                System.out.println("apache_closed_entity_read=" + expected.getClass().getSimpleName());
            }
        }
    }

    // check fails deterministically when a fixed SDK expectation is violated.
    private static void check(boolean condition) {
        if (!condition) {
            throw new AssertionError("third-party HTTP contract failed");
        }
    }
}
