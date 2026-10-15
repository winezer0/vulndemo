using Microsoft.AspNetCore.Mvc;

[ApiController]
public class Routes {
    [HttpGet("/first")] public string First() => ""; [HttpPost("/second")] public string Second() => "";
}
