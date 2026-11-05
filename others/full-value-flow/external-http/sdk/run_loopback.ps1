param(
    [Parameter(Mandatory)][string]$EntryExecutable,
    [string[]]$EntryArguments = @(),
    [string]$PhpExecutable = (Get-Command php -ErrorAction Stop).Source,
    [string]$VendorDirectory = '',
    [string]$LogDirectory = (Join-Path ([IO.Path]::GetTempPath()) 'w21-http-sdk-loopback')
)

$ErrorActionPreference = 'Stop'
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$taskListener = [Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback, 0)
$taskListener.Start()
$taskPort = $taskListener.LocalEndpoint.Port
$taskListener.Stop()
$taskRouter = '"' + (Join-Path $PSScriptRoot 'php/router.php') + '"'
$taskServer = Start-Process -FilePath $PhpExecutable -ArgumentList @('-S', "127.0.0.1:$taskPort", $taskRouter) -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $LogDirectory 'server.log') -RedirectStandardError (Join-Path $LogDirectory 'server-error.log')
$taskPreviousBase = $env:HTTP_SDK_BASE_URL
$taskPreviousVendor = $env:HTTP_SDK_PHP_VENDOR
try {
    $env:HTTP_SDK_BASE_URL = "http://127.0.0.1:$taskPort"
    $env:HTTP_SDK_PHP_VENDOR = $VendorDirectory
    $taskReady = $false
    for ($taskAttempt = 0; $taskAttempt -lt 30; $taskAttempt++) {
        try {
            Invoke-WebRequest -UseBasicParsing "$env:HTTP_SDK_BASE_URL/fixed" -TimeoutSec 1 | Out-Null
            $taskReady = $true
            break
        } catch { Start-Sleep -Milliseconds 100 }
    }
    if (-not $taskReady) { throw 'Loopback fixture did not become ready' }
    & $EntryExecutable @EntryArguments
    $taskExit = $LASTEXITCODE
} finally {
    $env:HTTP_SDK_BASE_URL = $taskPreviousBase
    $env:HTTP_SDK_PHP_VENDOR = $taskPreviousVendor
    if (-not $taskServer.HasExited) { Stop-Process -Id $taskServer.Id }
}
exit $taskExit
