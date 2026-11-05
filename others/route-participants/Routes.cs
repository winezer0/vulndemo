using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;
class Routes {
    static string Serve() => "ok";
    static ValueTask<object?> AuthFilter(EndpointFilterInvocationContext context, EndpointFilterDelegate next) => next(context);
    static void Register(WebApplication app) {
        app.MapGet("/csharp", Serve).AddEndpointFilter(AuthFilter);
    }
}
