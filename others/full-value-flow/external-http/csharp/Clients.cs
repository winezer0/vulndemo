using System.Net.Http;
using System.Threading.Tasks;
using RestSharp;

public static class HTTPClients {
    public static async Task<string> IndependentRequest(string ctorIsolatedInput) {
        var changed = new RestRequest(ctorIsolatedInput);
        var clean = new RestRequest("https://example.invalid/clean");
        var client = new RestClient();
        var response = await client.ExecuteAsync(clean);
        var ctorIsolatedOut = response.Content;
        return ctorIsolatedOut;
    }

    public static async Task<string> HttpBody(string httpClientInput) {
        var client = new HttpClient();
        var response = await client.GetAsync(httpClientInput);
        var content = response.Content;
        var httpClientOut = await content.ReadAsStringAsync();
        return httpClientOut;
    }

    public static async Task<string> RestBody(string restSharpInput) {
        var client = new RestClient();
        var request = new RestRequest(restSharpInput);
        var response = await client.ExecuteAsync(request);
        var restSharpOut = response.Content;
        return restSharpOut;
    }
}
