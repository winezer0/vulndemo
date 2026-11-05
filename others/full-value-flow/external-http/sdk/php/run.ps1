param(
    [ValidateSet('curl', 'guzzle')][string]$Mode = 'curl',
    [string]$PhpExecutable = (Get-Command php -ErrorAction Stop).Source,
    [string]$VendorDirectory = '',
    [string]$LogDirectory = (Join-Path ([IO.Path]::GetTempPath()) 'w21-http-sdk-php')
)

$ErrorActionPreference = 'Stop'
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$taskPhpDirectory = Split-Path $PhpExecutable
$taskArguments = @()
& $PhpExecutable -r "exit(extension_loaded('curl') ? 0 : 1);"
if ($LASTEXITCODE -ne 0) {
    $taskExtension = Join-Path $taskPhpDirectory 'ext/php_curl.dll'
    if (-not (Test-Path -LiteralPath $taskExtension)) { throw 'The curl extension is unavailable' }
    $taskArguments += "-dextension=$taskExtension"
}
if ($Mode -eq 'guzzle' -and -not (Test-Path -LiteralPath (Join-Path $VendorDirectory 'autoload.php'))) {
    throw 'VendorDirectory must contain the locked Guzzle installation'
}
$taskEntry = if ($Mode -eq 'curl') { 'runtime.php' } else { 'guzzle.php' }
$taskArguments += (Join-Path $PSScriptRoot $taskEntry)
& (Join-Path $PSScriptRoot '../run_loopback.ps1') -EntryExecutable $PhpExecutable -EntryArguments $taskArguments -PhpExecutable $PhpExecutable -VendorDirectory $VendorDirectory -LogDirectory $LogDirectory
exit $LASTEXITCODE
