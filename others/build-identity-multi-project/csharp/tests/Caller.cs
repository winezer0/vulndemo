namespace Acme.Tests;

using Acme.Service;

internal static class Caller
{
    internal static void Call() => ServiceApi.Run();
}
