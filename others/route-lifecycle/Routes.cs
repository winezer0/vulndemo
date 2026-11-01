using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Mvc;

class Startup {
    void Configure(IApplicationBuilder app) {
        app.UseMiddleware<Auth>();
    }
}

class Routes {
    [HttpGet("/cs")]
    public string Users() { return "ok"; }
}
