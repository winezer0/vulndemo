import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

class ApachePrecision {
    // executeOpen consumes an entity returned by the explicit-context overload.
    private static String executeOpen(String apacheOpenInput) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apacheOpenInput);
        org.apache.hc.core5.http.ClassicHttpResponse response = client.executeOpen(null, request, null);
        HttpEntity entity = response.getEntity();
        String apacheOpenOut = EntityUtils.toString(entity);
        response.close();
        client.close();
        return apacheOpenOut;
    }

    static byte[] bytes(String apacheBytesInput) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apacheBytesInput);
        org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        byte[] apacheBytesOut = EntityUtils.toByteArray(entity);
        return apacheBytesOut;
    }

    static String arbitrary(HttpEntity apacheArbitraryInput) throws Exception {
        String apacheArbitraryOut = EntityUtils.toString(apacheArbitraryInput);
        return apacheArbitraryOut;
    }

    static String custom(CloseableHttpClient client, String apacheCustomInput) throws Exception {
        HttpGet request = new HttpGet(apacheCustomInput);
        org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String apacheCustomOut = EntityUtils.toString(entity);
        return apacheCustomOut;
    }

    static String closed(String apacheClosedInput) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apacheClosedInput);
        org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = client.execute(request);
        HttpEntity apacheClosedSource = response.getEntity();
        response.close();
        String apacheClosedOut = EntityUtils.toString(apacheClosedSource);
        return apacheClosedOut;
    }
}
