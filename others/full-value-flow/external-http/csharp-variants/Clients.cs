using System.Threading;
using System.Threading.Tasks;
using RestSharp;

public static class RestSharpVariants {
    public static async Task<string> WithCancellation(string canceledInput, CancellationToken cancellation) {
        var client = new RestClient();
        var request = new RestRequest(canceledInput);
        var response = await client.ExecuteAsync(request, cancellation);
        var canceledOut = response.Content;
        return canceledOut;
    }
}
