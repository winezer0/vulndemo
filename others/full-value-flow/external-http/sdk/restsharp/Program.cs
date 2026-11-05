using System.Net;
using System.Text;
using RestSharp;

// Check rejects mismatched package behavior without external test dependencies.
static void Check(bool condition, string message)
{
    if (!condition) throw new InvalidOperationException(message);
}

using var http = new HttpClient(new FixedHandler());
using var client = new RestClient(http);
var request = new RestRequest("https://fixture.invalid/fixed");
Task<RestResponse> pending = client.ExecuteAsync(request);
var response = await pending;
Check(response.Content == "fixed-restsharp-body", "content mismatch");
Check(response.StatusCode == HttpStatusCode.Created, "status mismatch");
Check(Encoding.UTF8.GetString(response.RawBytes!) == "fixed-restsharp-body", "raw bytes mismatch");
Check(ReferenceEquals(response.Request, request), "request identity mismatch");
using var canceled = new CancellationTokenSource();
canceled.Cancel();
var canceledResponse = await client.ExecuteAsync(new RestRequest("https://fixture.invalid/canceled"), canceled.Token);
Check(canceledResponse.ResponseStatus == ResponseStatus.Aborted, "canceled response status mismatch");
Check(canceledResponse.Content is null, "canceled response must not contain a body");
Console.WriteLine($"runtime={Environment.Version}; restsharp={typeof(RestClient).Assembly.GetName().Version}; cases=6; status=passed");

// FixedHandler supplies deterministic content through RestSharp's actual HttpClient adapter.
sealed class FixedHandler : HttpMessageHandler
{
    // SendAsync honors cancellation and returns a fixed payload.
    protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
    {
        cancellationToken.ThrowIfCancellationRequested();
        return Task.FromResult(new HttpResponseMessage(HttpStatusCode.Created)
        {
            Content = new StringContent("fixed-restsharp-body", Encoding.UTF8)
        });
    }
}
