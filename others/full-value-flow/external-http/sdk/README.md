# HTTP SDK 契约实测

这些程序验证真实 SDK 的载体、消费入口和失败边界，不替代图查询或实际 MGSAST 引擎效果验收。使用本机响应服务或确定性的自定义 transport，不访问业务服务。依赖缓存和构建输出放在仓库外。

| 入口 | 固定 API 依赖 | 实际执行结果 |
|---|---|---|
| `go/body_runtime_test.go` | Go 标准库 net/http、io | 5 个子测试通过；固定正文、HTTP 错误正文、部分读取、取消、自定义 transport |
| `node/runtime.mjs` | Axios 1.7.7，Node 类型 22.10.1 | Node 22.22.3，14 项通过；含响应拦截器、独立客户端及流首块 |
| `node/contracts.ts` | TypeScript 5.6.3 | 类型检查通过，未 await 的 Promise.text 是预期错误 |
| `python/runtime_test.py` | Requests 2.32.3、HTTPX 0.27.2，传递依赖见 requirements.lock | Python 3.14.6，5 个测试通过 |
| `csharp/HttpContracts.csproj` | net8.0，无第三方包 | Runtime 8.0.28，13 项正文/状态契约检查通过；默认网络、自定义 handler 和同一部分读取 fixture |
| `java/HttpContracts.java` | JDK 21，无第三方包 | JDK 21.0.3，15 项通过；同步、异步、二进制、部分流、替换及丢弃 handler，含同一 HTTP 缓冲区 fixture |
| `restsharp/RestSharpContracts.csproj` | RestSharp 112.1.0，锁文件固定包哈希 | Runtime 8.0.28，6 项通过；Task、Content、RawBytes、status、请求身份及预先取消 |
| `rust/Cargo.toml` | reqwest 0.12.9、ureq 2.12.1、Tokio 1.41.1，Cargo.lock 固定传递依赖 | 9 项通过；同步/异步、未执行 Future、二进制、部分读及 HTTP 错误 |
| `php/runtime.php` | PHP cURL 扩展 | PHP 8.2.9、libcurl 7.85.0，11 项通过；字符串/输出/回调/STDIO 流、配置切换及 HTTP 错误 |
| `php/guzzle.php` | Guzzle 7.9.2，composer.lock 固定传递依赖 | PHP 8.2.9，14 项通过；含同一静态 fixture 的 Promise、自定义 handler 按值/引用/别名/预存及分步配置捕获、流与错误响应 |
| `jvm` | Kotlin 2.0.21、OkHttp 4.12.0、Apache 5.4.1、Ktor 3.0.1 | JDK 21.0.3，26 项检查通过，含同一 Kotlin HTTP fixture 的流关闭和部分读取行为；Gradle 8.10.2 锁定传递依赖 |
| `native` | libcurl 8.11.0、CPR 1.11.1 | GCC 16.1.0，C/C++ 各 62 项检查通过（计数受轮询次数影响），含复用与独立句柄的不同响应；CPR 11 项通过，含会话复用、同步/异步 fixture 和异步所有权反例；仅 HTTP |

Go fasthttp 的运行测试位于相邻 `../fasthttp/precision_runtime_test.go`，依赖固定为 1.58.0；四个包级 Get/GetTimeout/GetDeadline/Post 提前失败返回旧 dst 的实测通过。相邻 Resty 2.16.5 生命周期运行测试复核通过。表中只覆盖已列出的有限运行变体；SDK 成功不等于全部语言的图内精确模型和生命周期验收完成。

## 运行方式

下列缓存路径为本轮实际路径，可换成其他仓库外目录。Go 测试仍使用项目规定的稳定 GOCACHE/GOMODCACHE。

```powershell
$env:HTTP_SDK_NODE_ROOT = 'C:/Windows/Temp/w21-http-sdk-node'
node node/runtime.mjs
node "$env:HTTP_SDK_NODE_ROOT/node_modules/typescript/bin/tsc" --noEmit --strict --target ES2022 --module ESNext --moduleResolution node --allowSyntheticDefaultImports --baseUrl "$env:HTTP_SDK_NODE_ROOT/node_modules" --typeRoots "$env:HTTP_SDK_NODE_ROOT/node_modules/@types" node/contracts.ts
C:/Windows/Temp/w21-http-sdk-python/Scripts/python.exe python/runtime_test.py
dotnet run --project csharp/HttpContracts.csproj --artifacts-path C:/Windows/Temp/w21-http-sdk-csharp --configuration Release
javac --add-modules jdk.httpserver -d C:/Windows/Temp/w21-http-java-classes java/HttpContracts.java ../java/StreamBuffer.java ../java/Precision.java
java --add-modules jdk.httpserver -cp C:/Windows/Temp/w21-http-java-classes HttpContracts
dotnet run --project restsharp/RestSharpContracts.csproj --artifacts-path C:/Windows/Temp/w21-http-sdk-restsharp --configuration Release -p:RestoreLockedMode=true
$env:CARGO_TARGET_DIR = 'C:/Windows/Temp/w21-http-sdk-rust'
cargo run --locked --manifest-path rust/Cargo.toml
./php/run.ps1 -Mode curl
./php/run.ps1 -Mode guzzle -VendorDirectory C:/Windows/Temp/w21-http-sdk-guzzle/vendor
./run_loopback.ps1 -EntryExecutable C:/Windows/Temp/w21-http-sdk-native-build/curl_c_contracts.exe
./run_loopback.ps1 -EntryExecutable C:/Windows/Temp/w21-http-sdk-native-build/curl_cpp_contracts.exe
./run_loopback.ps1 -EntryExecutable C:/Windows/Temp/w21-http-sdk-native-build/cpr_contracts.exe
```

Node 依赖使用 `node/package.json` 安装到上述外部缓存；Python 使用 `python/requirements.lock`。当前成功日志暂存在 `C:/Windows/Temp/http-w21-*-sdk.log`，正式归档及清单哈希尚待最终收口。

Go 独立 SDK 模块需关闭 workspace，仍使用项目规定的稳定缓存：

```powershell
$env:GOWORK = 'off'
$env:GOCACHE = 'C:/ISEC/Deving/mgcodesec/gobuild'
$env:GOMODCACHE = 'C:/ISEC/Deving/mgcodesec/gomod'
Push-Location go
try { go test ./... -count=1 } finally { Pop-Location }
Push-Location ../fasthttp
try { go test ./... -run '^TestSDK' -count=1 } finally { Pop-Location }
Push-Location ../resty
try { go test ./... -run '^TestRestyLifecycleRuntime$' -count=1 } finally { Pop-Location }
```

JVM 使用 Gradle 8.10.2，官方分发包 SHA256 为 `31c55713e40233a8303827ceb42ca48a47267a0ad4bab9177123121e71524c26`，构建及项目缓存均放在 Temp。`jvm/gradle.lockfile` 固定运行依赖；执行时通过 `HTTP_SDK_JVM_BUILD` 指定外部构建目录。

```powershell
$env:GRADLE_USER_HOME = 'C:/Windows/Temp/w21-http-sdk-gradle-cache'
$env:HTTP_SDK_JVM_BUILD = 'C:/Windows/Temp/w21-http-sdk-jvm-build'
C:/Windows/Temp/w21-http-sdk-gradle/gradle-8.10.2/bin/gradle.bat --no-daemon --console=plain --project-cache-dir C:/Windows/Temp/w21-http-sdk-jvm-project-cache -p jvm run
```

原生 SDK 从 libcurl 8.11.0 / CPR 1.11.1 固定源码编译，源包 SHA256 分别为 `db59cf0d671ca6e7f5c2c5ec177084a33a79e04c97e71cf183a5cdea235054eb`、`e84b8ef348f41072609f53aab05bdaab24bf5916c62d99651dfbeaf282a8e0a2`。这两个哈希是实际下载后记录的源包身份。libcurl 使用 HTTP_ONLY、关闭 SSL 及额外压缩依赖，安装到外部 prefix；native CMake 的 CPR_SOURCE_DIR 指向固定源码，CMAKE_PREFIX_PATH 指向该 prefix。该构建不验收 TLS。

在固定源包解压完成后，实际编译入口如下（MinGW64 GCC/Ninja 已在 PATH）：

```powershell
cmake -S C:/Windows/Temp/w21-http-sdk-native-src/curl-8.11.0 -B C:/Windows/Temp/w21-http-sdk-curl-build -G Ninja -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=C:/Windows/Temp/w21-http-sdk-curl-prefix -DBUILD_SHARED_LIBS=OFF -DBUILD_CURL_EXE=OFF -DBUILD_TESTING=OFF -DHTTP_ONLY=ON -DCURL_ENABLE_SSL=OFF -DCURL_USE_OPENSSL=OFF -DCURL_USE_LIBPSL=OFF -DCURL_ZLIB=OFF -DCURL_BROTLI=OFF -DCURL_ZSTD=OFF -DUSE_NGHTTP2=OFF -DCURL_USE_LIBSSH2=OFF
cmake --build C:/Windows/Temp/w21-http-sdk-curl-build --parallel 2
cmake --install C:/Windows/Temp/w21-http-sdk-curl-build
cmake -S native -B C:/Windows/Temp/w21-http-sdk-native-build -G Ninja -DCMAKE_BUILD_TYPE=Release -DCMAKE_PREFIX_PATH=C:/Windows/Temp/w21-http-sdk-curl-prefix -DCPR_SOURCE_DIR=C:/Windows/Temp/w21-http-sdk-native-src/cpr-1.11.1
cmake --build C:/Windows/Temp/w21-http-sdk-native-build --parallel 2
```

## 不能省略的语义边界

- .NET 自定义 handler 可产生自行构造的正文；局部 Content 模型不证明整个请求没有未知依赖。同一客户端的历史候选仍保留，默认 receiver 状态证明尚待完善。
- JDK BodyHandler 决定 `body()` 的实际类型及值。`replacing` 返回本地替换值，`discarding` 返回 null，不能无条件将全部 send 返回体标成外部正文。依据：[JDK 21 BodyHandlers](https://docs.oracle.com/en/java/javase/21/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html)。
- urllib 的 HTTP 与 data URL 返回类型不同，不能仅按 urlopen 函数名绑定 HTTPResponse。
- SDK 返回值正确不代表本项目路径完整；图内的正例、隔离、未知诊断和精确证明必须分别断言。
- fasthttp 的正文返回值不总是独立的远端内容：不支持的 URI 可在 reset 前失败并返回旧 dst。正文模型必须同时保留实际 dst 来源，不能只精化为外部内容。
- PHP CURLOPT_FILE 需要可表示为 STDIO 的流；本轮 php://memory 被拒绝，tmpfile 通过。实际 PHP/libcurl 版本不同于 API 基准，不混用版本结论。
