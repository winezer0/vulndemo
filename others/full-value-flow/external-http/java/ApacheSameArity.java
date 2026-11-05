import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

class ApacheSameArity {
    // executeOpen has the same name and arity as the external receiver method.
    private static String executeOpen(String apacheSameInput, boolean unused, int count) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apacheSameInput);
        ClassicHttpResponse response = client.executeOpen(null, request, null);
        HttpEntity entity = response.getEntity();
        String apacheSameOut = EntityUtils.toString(entity);
        response.close();
        client.close();
        return apacheSameOut;
    }
}
