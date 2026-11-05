# 外部 HTTP 来源链回归

本目录验证真实调用语法在 FullValueFlow 图中的来源传播。测试通过不表示执行了网络请求，也不表示所有客户端已有精确库模型。

## 验收边界

- fasthttp 的包级 Get/GetTimeout/GetDeadline/Post 使用 v1.58.0 精确响应体模型：body 是返回槽位 1，status/error 不承接该内容。
- fasthttp Client/HostClient、Do 输出对象，以及下表其他语言客户端，验证通用保守候选链；路径必须保留 opaque 缺口，不能据此解除未知状态、回调执行或内容语义缺口。
- Memory/SQLite、正反向、关闭重开分别检查。独立对象、晚写和本地同名反例用于检查错误归并。
- Go fasthttp fixture 使用 go.mod/go.sum 固定依赖并完成编译；其他语言 fixture 用于解析和图查询，没有据此声明 SDK 编译或实际网络运行验收。C/C++ 回调注册也不构成 perform 已执行的证明。

## API 依据

| 语言 | 客户端与 API 依据 | 本轮语义 |
|---|---|---|
| Go | [fasthttp v1.58.0](https://github.com/valyala/fasthttp/blob/v1.58.0/client.go)，Resty v2.16.5 | 包级 body 精确；实例及输出对象候选 |
| JavaScript/TypeScript | [Fetch](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)、[Axios response](https://axios-http.com/docs/res_schema)、[Node HTTP](https://nodejs.org/api/http.html#httpgetoptions-callback) | Promise、字段及两级 callback 候选 |
| Python | [Requests](https://requests.readthedocs.io/en/latest/user/quickstart/)、[HTTPX](https://www.python-httpx.org/quickstart/)、[urllib](https://docs.python.org/3/library/urllib.request.html#urllib.request.urlopen) | 返回对象及内容消费候选 |
| PHP | [cURL](https://www.php.net/manual/en/function.curl-exec.php)、[Guzzle 7](https://docs.guzzlephp.org/en/stable/quickstart.html) | 配置及响应消费候选 |
| C# | [HttpClient](https://learn.microsoft.com/en-us/dotnet/api/system.net.http.httpclient.getasync)、[RestSharp](https://restsharp.dev/docs/usage/response/) | 构造参数、async 及内容字段候选 |
| Rust | [reqwest 0.12.9](https://docs.rs/reqwest/0.12.9/reqwest/)、[ureq 2.12.1](https://docs.rs/ureq/2.12.1/ureq/) | Result、await 及响应消费候选 |
| Java | [JDK 21 HTTP](https://docs.oracle.com/en/java/javase/21/docs/api/java.net.http/java/net/http/HttpClient.html)、[OkHttp](https://square.github.io/okhttp/)、[Apache HttpClient 5](https://hc.apache.org/httpcomponents-client-5.5.x/quickstart.html) | builder 状态、构造参数及响应消费候选 |
| Kotlin | [OkHttp](https://square.github.io/okhttp/)、[Ktor](https://ktor.io/docs/client-responses.html) | builder、挂起调用及响应消费候选 |
| C/C++ | [libcurl 8.11.0](https://github.com/curl/curl/blob/curl-8_11_0/include/curl/curl.h)、[CPR 1.11.1](https://github.com/libcpr/cpr/blob/1.11.1/include/cpr/api.h) | 指针状态、easy/multi callback 和响应字段候选 |

## 固定 API 基准

以下固定版本源码用于复核 fixture 的 API 形态，不代表已安装或运行对应 SDK。动态内容、流式消费和执行时序仍按上面的候选边界处理。

| 依赖 | 固定基准 |
|---|---|
| Node HTTP/HTTPS、Fetch 运行时 | [Node 22.11.0 文档](https://nodejs.org/download/release/v22.11.0/docs/api/http.html)；Fetch 的标准语义见上文 |
| Axios | [1.7.7 声明](https://raw.githubusercontent.com/axios/axios/v1.7.7/index.d.ts) |
| Requests / HTTPX | [2.32.3](https://raw.githubusercontent.com/psf/requests/v2.32.3/src/requests/models.py) / [0.27.2](https://raw.githubusercontent.com/encode/httpx/0.27.2/httpx/_models.py) |
| urllib.request | [CPython 3.12.7](https://raw.githubusercontent.com/python/cpython/v3.12.7/Lib/urllib/request.py) |
| PHP cURL / Guzzle | [PHP 8.3.13](https://raw.githubusercontent.com/php/php-src/php-8.3.13/ext/curl/curl.stub.php) / [Guzzle 7.9.2](https://raw.githubusercontent.com/guzzle/guzzle/7.9.2/src/Client.php) |
| HttpClient / RestSharp | [.NET 8.0.11](https://raw.githubusercontent.com/dotnet/runtime/v8.0.11/src/libraries/System.Net.Http/src/System/Net/Http/HttpClient.cs) / [RestSharp 112.1.0](https://raw.githubusercontent.com/restsharp/RestSharp/112.1.0/src/RestSharp/RestClient.Extensions.cs) |
| JDK / OkHttp / Apache | JDK 21；[OkHttp 4.12.0](https://raw.githubusercontent.com/square/okhttp/parent-4.12.0/okhttp/src/main/kotlin/okhttp3/Response.kt)；[HttpClient 5.4.1](https://raw.githubusercontent.com/apache/httpcomponents-client/rel/v5.4.1/httpclient5/src/main/java/org/apache/hc/client5/http/classic/methods/HttpGet.java) |
| Ktor | [3.0.1](https://raw.githubusercontent.com/ktorio/ktor/3.0.1/ktor-client/ktor-client-core/common/src/io/ktor/client/statement/HttpResponse.kt) |
| reqwest / ureq / libcurl / CPR | 0.12.9 / 2.12.1 / 8.11.0 / 1.11.1，固定来源见上表 |

实际引擎扫描效果不在本轮验收范围。主方案中其他消费变体、精确 userdata/handle 时序以及跨版本 SDK 执行验收，不能由这些代表性候选链测试推定完成。
