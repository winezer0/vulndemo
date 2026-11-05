import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class Clients {
    public static String isolatedState(String stateIsolatedInput) throws Exception {
        Request.Builder changed = new Request.Builder();
        Request.Builder clean = new Request.Builder();
        changed.url(stateIsolatedInput);
        Request request = clean.build();
        okhttp3.Call call = new OkHttpClient().newCall(request);
        Response response = call.execute();
        String stateIsolatedOut = response.body().string();
        return stateIsolatedOut;
    }

    public static String laterState(String stateLateInput) throws Exception {
        Request.Builder builder = new Request.Builder();
        Request request = builder.build();
        builder.url(stateLateInput);
        okhttp3.Call call = new OkHttpClient().newCall(request);
        Response response = call.execute();
        String stateLateOut = response.body().string();
        return stateLateOut;
    }

    public static String jdkBody(String jdkInput) throws Exception {
        URI uri = URI.create(jdkInput);
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
        HttpRequest request = builder.build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jdkOut = response.body();
        return jdkOut;
    }

    public static String okhttpBody(String okhttpInput) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(okhttpInput);
        Request request = builder.build();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        okhttp3.ResponseBody body = response.body();
        String okhttpOut = body.string();
        return okhttpOut;
    }

    public static String apacheBody(String apacheInput) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apacheInput);
        org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = client.execute(request);
        org.apache.hc.core5.http.HttpEntity entity = response.getEntity();
        String apacheOut = EntityUtils.toString(entity);
        return apacheOut;
    }
}
