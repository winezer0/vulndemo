using System.Net;
using System.Net.Sockets;
using System.Text;

// Check rejects a mismatched SDK contract without a test framework dependency.
static void Check(bool condition, string message)
{
    if (!condition) throw new InvalidOperationException(message);
}

using var client = new HttpClient(new FixedHandler());
Task<HttpResponseMessage> pending = client.GetAsync("https://fixture.invalid/first");
using var first = await pending;
using var second = await client.GetAsync("https://fixture.invalid/second");
Check(first.StatusCode == HttpStatusCode.Created, "status slot mismatch");
Check(await first.Content.ReadAsStringAsync() == "body:first", "first content mismatch");
Check(await second.Content.ReadAsStringAsync() == "body:second", "response isolation mismatch");
Check(Encoding.UTF8.GetString(await first.Content.ReadAsByteArrayAsync()) == "body:first", "bytes mismatch");
using (var stream = await second.Content.ReadAsStreamAsync())
{
    var prefix = new byte[4];
    Check(await stream.ReadAsync(prefix) == 4, "partial read length mismatch");
    Check(Encoding.UTF8.GetString(prefix) == "body", "partial read content mismatch");
}
using var cancellation = new CancellationTokenSource();
cancellation.Cancel();
var cancelled = false;
try { using var ignored = await client.GetAsync("https://fixture.invalid/cancel", cancellation.Token); }
catch (OperationCanceledException) { cancelled = true; }
Check(cancelled, "cancelled request unexpectedly completed");
Check(first.Content is HttpContent, "content carrier mismatch");
using var listener = new TcpListener(IPAddress.Loopback, 0);
listener.Start();
var address = (IPEndPoint)listener.LocalEndpoint;
var server = RespondOnce(listener);
using (var networkClient = new HttpClient { Timeout = TimeSpan.FromSeconds(5) })
using (var networkResponse = await networkClient.GetAsync($"http://127.0.0.1:{address.Port}/fixed"))
{
    Check(networkResponse.StatusCode == HttpStatusCode.Accepted, "network status mismatch");
    Check(await networkResponse.Content.ReadAsStringAsync() == "fixed-network-body", "network body mismatch");
}
await server;
var secondServer = RespondOnce(listener);
var fixtureBody = await HTTPPrecision.PartialRead($"http://127.0.0.1:{address.Port}/fixture");
var fixturePrefix = Encoding.UTF8.GetString(fixtureBody).TrimEnd('\0');
Check(fixturePrefix.Length > 0 && "fixed-network-body".StartsWith(fixturePrefix), "fixture partial read mismatch");
await secondServer;
var thirdServer = RespondOnce(listener);
var sharedBuffer = new byte[4];
var parameterBuffer = await HTTPPrecision.PartialReadTo($"http://127.0.0.1:{address.Port}/parameter", sharedBuffer, sharedBuffer);
var parameterPrefix = Encoding.UTF8.GetString(parameterBuffer).TrimEnd('\0');
Check(parameterPrefix.Length > 0 && "fixed-network-body".StartsWith(parameterPrefix), "parameter partial read mismatch");
await thirdServer;
var fourthServer = RespondOnce(listener);
var defaultBuffer = await HTTPPrecision.DefaultPartialRead($"http://127.0.0.1:{address.Port}/default");
var defaultPrefix = Encoding.UTF8.GetString(defaultBuffer).TrimEnd('\0');
Check(defaultPrefix.Length > 0 && "fixed-network-body".StartsWith(defaultPrefix), "default partial read mismatch");
await fourthServer;
Console.WriteLine($"runtime={Environment.Version}; cases=13; status=passed; handlers=custom,default");

// RespondOnce serves one fixed loopback response through the default network transport.
static async Task RespondOnce(TcpListener listener)
{
    using var connection = await listener.AcceptTcpClientAsync();
    using var stream = connection.GetStream();
    using var timeout = new CancellationTokenSource(TimeSpan.FromSeconds(5));
    var request = new byte[4096];
    var read = await stream.ReadAsync(request, timeout.Token);
    Check(read > 0, "empty network request");
    var response = Encoding.ASCII.GetBytes("HTTP/1.1 202 Accepted\r\nContent-Length: 18\r\nConnection: close\r\n\r\nfixed-network-body");
    await stream.WriteAsync(response, timeout.Token);
}

// FixedHandler proves custom transports can produce content unrelated to the URL value.
sealed class FixedHandler : HttpMessageHandler
{
    // SendAsync returns deterministic content and honors cancellation.
    protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
    {
        cancellationToken.ThrowIfCancellationRequested();
        var body = request.RequestUri!.AbsolutePath == "/first" ? "body:first" : "body:second";
        return Task.FromResult(new HttpResponseMessage(HttpStatusCode.Created)
        {
            Content = new StringContent(body, Encoding.UTF8)
        });
    }
}
