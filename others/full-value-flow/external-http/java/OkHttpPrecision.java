import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class OkHttpPrecision {
    static byte[] bytes(String okhttpBytesInput) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(okhttpBytesInput);
        Request request = builder.build();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        byte[] okhttpBytesOut = body.bytes();
        return okhttpBytesOut;
    }

    static java.io.InputStream stream(String okhttpStreamInput) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(okhttpStreamInput);
        Request request = builder.build();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        java.io.InputStream okhttpStreamOut = body.byteStream();
        return okhttpStreamOut;
    }

    static String independent(String okhttpFirstInput, String okhttpSecondInput) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request.Builder firstBuilder = new Request.Builder();
        firstBuilder.url(okhttpFirstInput);
        Request firstRequest = firstBuilder.build();
        okhttp3.Call firstCall = client.newCall(firstRequest);
        Response firstResponse = firstCall.execute();
        ResponseBody firstBody = firstResponse.body();
        String okhttpFirstOut = firstBody.string();
        Request.Builder secondBuilder = new Request.Builder();
        secondBuilder.url(okhttpSecondInput);
        Request secondRequest = secondBuilder.build();
        okhttp3.Call secondCall = client.newCall(secondRequest);
        Response secondResponse = secondCall.execute();
        ResponseBody secondBody = secondResponse.body();
        String okhttpSecondOut = secondBody.string();
        return okhttpFirstOut + okhttpSecondOut;
    }

    static String arbitrary(ResponseBody okhttpArbitraryInput) throws Exception {
        String okhttpArbitraryOut = okhttpArbitraryInput.string();
        return okhttpArbitraryOut;
    }

    static String custom(OkHttpClient client, String okhttpCustomInput) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.url(okhttpCustomInput);
        Request request = builder.build();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        String okhttpCustomOut = body.string();
        return okhttpCustomOut;
    }

    static String closedParent(String closedParentInput) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(closedParentInput);
        Request request = builder.build();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody closedParentSource = response.body();
        response.close();
        String closedParentOut = closedParentSource.string();
        return closedParentOut;
    }
}
