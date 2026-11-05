using System.Net.Http;
using System.Threading.Tasks;

public static class HTTPPrecision {
    public static async Task<byte[]> DefaultPartialRead(string defaultBufferInput) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(defaultBufferInput);
        using var stream = await response.Content.ReadAsStreamAsync();
        var buffer = new byte[4];
        await stream.ReadAsync(buffer, 0, buffer.Length);
        var defaultBufferOut = buffer;
        return defaultBufferOut;
    }

    public static async Task<byte[]> PartialReadTo(string paramReadInput, byte[] paramReadBuffer, byte[] paramReadOther) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(paramReadInput, HttpCompletionOption.ResponseHeadersRead);
        using var stream = await response.Content.ReadAsStreamAsync();
        await stream.ReadAsync(paramReadBuffer, 0, paramReadBuffer.Length);
        var paramReadAliased = paramReadOther[0];
        var paramReadOut = paramReadBuffer;
        return paramReadOut;
    }

    public static async Task<byte[]> PartialRead(string partialReadInput) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(partialReadInput, HttpCompletionOption.ResponseHeadersRead);
        using var stream = await response.Content.ReadAsStreamAsync();
        var buffer = new byte[4];
        var other = new byte[4];
        var alias = buffer;
        var partialReadBefore = buffer[0];
        await stream.ReadAsync(buffer, 0, buffer.Length);
        var partialReadAfter = alias[0];
        var partialReadOther = other[0];
        var partialReadOut = buffer;
        return partialReadOut;
    }

    // Bytes retains the actual awaited binary consumer and its response field.
    public static async Task<byte[]> Bytes(string byteInput) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(byteInput);
        var byteOut = await response.Content.ReadAsByteArrayAsync();
        return byteOut;
    }

    // Stream retains the actual stream carrier; later reads still need their own evidence.
    public static async Task<System.IO.Stream> Stream(string streamInput) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(streamInput);
        var streamOut = await response.Content.ReadAsStreamAsync();
        return streamOut;
    }

    // SeparateClients excludes state belonging to another client allocation.
    public static async Task<string> SeparateClients(string clientIsolatedInput) {
        using var changedClient = new HttpClient();
        using var cleanClient = new HttpClient();
        using var changedResponse = await changedClient.GetAsync(clientIsolatedInput);
        using var cleanResponse = await cleanClient.GetAsync("https://example.invalid/fixed");
        var clientIsolatedOut = await cleanResponse.Content.ReadAsStringAsync();
        return clientIsolatedOut;
    }

    // SeparateResponses keeps content associated with its actual response object.
    public static async Task<string> SeparateResponses(string responseIsolatedInput) {
        using var client = new HttpClient();
        using var changedResponse = await client.GetAsync(responseIsolatedInput);
        using var cleanResponse = await client.GetAsync("https://example.invalid/fixed");
        var responseIsolatedOut = await cleanResponse.Content.ReadAsStringAsync();
        return responseIsolatedOut;
    }

    // UnknownOptions retains the unmodeled overload and its conservative dependency.
    public static async Task<string> UnknownOptions(string optionInput) {
        using var client = new HttpClient();
        using var response = await client.GetAsync(optionInput, HttpCompletionOption.ResponseHeadersRead);
        var optionContent = response.Content;
        var optionOut = await optionContent.ReadAsStringAsync();
        return optionOut;
    }
}
