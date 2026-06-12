using csvulnweb.CmdExec;
using csvulnweb.CodeExec;
using csvulnweb.SSRF;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

// 启用静态文件服务（用于访问wwwroot/index.html）
app.UseDefaultFiles();
app.UseStaticFiles();

// ==================== 命令执行漏洞路由 ====================

// CmdExec - 子类继承调用（外部参数）
app.MapGet("/cmd/subclass", async (HttpContext context) =>
{
    string cmd = context.Request.Query["cmd"].FirstOrDefault() ?? "echo default";
    var test = new CmdExecSubClassTest();
    string result = test.TestCmdExec(cmd);
    return Results.Content(result, "text/plain");
});

// CmdExec - 子类继承调用（硬编码）
app.MapGet("/cmd/subclass/hardcoded", async (HttpContext context) =>
{
    var test = new CmdExecSubClassTest();
    string result = test.TestHardcodedCmdExec();
    return Results.Content(result, "text/plain");
});

// CmdExec - 接口实现类调用（外部参数）
app.MapGet("/cmd/interface", async (HttpContext context) =>
{
    string cmd = context.Request.Query["cmd"].FirstOrDefault() ?? "echo default";
    var impl = new CmdExecInterfaceImpl();
    string result = impl.ExecuteCommand(cmd);
    return Results.Content(result, "text/plain");
});

// CmdExec - 接口实现类调用（硬编码）
app.MapGet("/cmd/interface/hardcoded", async (HttpContext context) =>
{
    var impl = new CmdExecInterfaceImpl();
    string result = impl.HardcodedCommand();
    return Results.Content(result, "text/plain");
});

// ==================== 代码执行漏洞路由 ====================

// CodeExec - 子类继承调用（外部参数）
app.MapGet("/code/subclass", async (HttpContext context) =>
{
    string code = context.Request.Query["code"].FirstOrDefault() ?? "1+1";
    var test = new CodeExecSubClassTest();
    string result = test.TestCodeExec(code);
    return Results.Content(result, "text/plain");
});

// CodeExec - 子类继承调用（硬编码）
app.MapGet("/code/subclass/hardcoded", async (HttpContext context) =>
{
    var test = new CodeExecSubClassTest();
    string result = test.TestHardcodedCodeExec();
    return Results.Content(result, "text/plain");
});

// CodeExec - 接口实现类调用（外部参数）
app.MapGet("/code/interface", async (HttpContext context) =>
{
    string code = context.Request.Query["code"].FirstOrDefault() ?? "1+1";
    var impl = new CodeExecInterfaceImpl();
    string result = impl.ExecuteCode(code);
    return Results.Content(result, "text/plain");
});

// CodeExec - 接口实现类调用（硬编码）
app.MapGet("/code/interface/hardcoded", async (HttpContext context) =>
{
    var impl = new CodeExecInterfaceImpl();
    string result = impl.HardcodedCode();
    return Results.Content(result, "text/plain");
});

// ==================== SSRF漏洞路由 ====================

// SSRF - 子类继承调用（外部参数）
app.MapGet("/ssrf/subclass", async (HttpContext context) =>
{
    string url = context.Request.Query["url"].FirstOrDefault() ?? "http://example.com";
    var test = new SSRFSubClassTest();
    string result = await test.TestSSRF(url);
    return Results.Content(result, "text/plain");
});

// SSRF - 子类继承调用（硬编码）
app.MapGet("/ssrf/subclass/hardcoded", async (HttpContext context) =>
{
    var test = new SSRFSubClassTest();
    string result = await test.TestHardcodedSSRF();
    return Results.Content(result, "text/plain");
});

// SSRF - 接口实现类调用（外部参数）
app.MapGet("/ssrf/interface", async (HttpContext context) =>
{
    string url = context.Request.Query["url"].FirstOrDefault() ?? "http://example.com";
    var impl = new SSRFInterfaceImpl();
    string result = await impl.ExecuteSSRF(url);
    return Results.Content(result, "text/plain");
});

// SSRF - 接口实现类调用（硬编码）
app.MapGet("/ssrf/interface/hardcoded", async (HttpContext context) =>
{
    var impl = new SSRFInterfaceImpl();
    string result = await impl.HardcodedSSRFRequest();
    return Results.Content(result, "text/plain");
});

// ==================== 跨文件调用路由 ====================

// 跨文件函数调用 - 命令执行
app.MapGet("/funccall/cmd", async (HttpContext context) =>
{
    string cmd = context.Request.Query["cmd"].FirstOrDefault() ?? "echo default";
    string result = csvulnweb.FuncCall.CrossFileCallCmdExec(cmd);
    return Results.Content(result, "text/plain");
});

// 跨文件函数调用 - 代码执行
app.MapGet("/funccall/code", async (HttpContext context) =>
{
    string code = context.Request.Query["code"].FirstOrDefault() ?? "1+1";
    string result = csvulnweb.FuncCall.CrossFileCallCodeExec(code);
    return Results.Content(result, "text/plain");
});

// 跨文件函数调用 - SSRF
app.MapGet("/funccall/ssrf", async (HttpContext context) =>
{
    string url = context.Request.Query["url"].FirstOrDefault() ?? "http://example.com";
    string result = await csvulnweb.FuncCall.CrossFileCallSSRF(url);
    return Results.Content(result, "text/plain");
});

// 跨文件函数调用 - 通过子类
app.MapGet("/funccall/child", async (HttpContext context) =>
{
    string cmd = context.Request.Query["cmd"].FirstOrDefault() ?? "echo default";
    string result = csvulnweb.FuncCall.CrossFileCallViaChild(cmd);
    return Results.Content(result, "text/plain");
});

// 跨文件函数调用 - 硬编码测试
app.MapGet("/funccall/hardcoded", async (HttpContext context) =>
{
    string cmdResult = csvulnweb.FuncCall.HardcodedCrossFileCmdExec();
    string codeResult = csvulnweb.FuncCall.HardcodedCrossFileCodeExec();
    string ssrfResult = await csvulnweb.FuncCall.HardcodedCrossFileSSRF();
    return Results.Content($"CMD: {cmdResult}\nCODE: {codeResult}\nSSRF: {ssrfResult}", "text/plain");
});

app.Run();
