<?php
require_once __DIR__ . '/../../php/clients.php';
// check rejects mismatched SDK behavior without external test dependencies.
function check(bool $condition, string $message): void {
    if (!$condition) {
        throw new RuntimeException($message);
    }
}

check(extension_loaded('curl'), 'curl extension is required');
$base = getenv('HTTP_SDK_BASE_URL');
check(is_string($base) && $base !== '', 'loopback server URL is required');
[$firstResult, $falseResult, $echoResult, $lastResult] = curl_option_sequence($base . '/fixed');
check($firstResult === 'fixed-php-body' && $falseResult === true && $echoResult === 'fixed-php-body' && $lastResult === 'fixed-php-body', 'return transfer sequence mismatch');
$handle = curl_init($base . '/fixed');
check($handle !== false, 'curl init failed');
check(curl_setopt_array($handle, [CURLOPT_RETURNTRANSFER => true, CURLOPT_TIMEOUT => 5]), 'return transfer configuration failed');
check(curl_exec($handle) === 'fixed-php-body', 'return transfer body mismatch');
check(curl_getinfo($handle, CURLINFO_RESPONSE_CODE) === 201, 'status mismatch');
check(curl_parameter($handle, $base . '/second') === 'second-php-body', 'parameter handle replacement failed');
check(php_receiver(new FixtureCarrier(), 'receiver-value') === 'receiver-value', 'receiver mutation failed');
check(curl_callback($base . '/fixed') === 'fixed-php-body', 'fixture callback body mismatch');
check(curl_setopt($handle, CURLOPT_URL, $base . '/second'), 'URL replacement failed');
check(curl_exec($handle) === 'second-php-body', 'reused handle did not use the replacement URL');
check(curl_setopt($handle, CURLOPT_URL, $base . '/fixed'), 'URL reset failed');
check(curl_setopt($handle, CURLOPT_RETURNTRANSFER, false), 'echo configuration failed');
ob_start();
$success = curl_exec($handle);
$echoed = ob_get_clean();
check($success === true && $echoed === 'fixed-php-body', 'echo transfer mismatch');
$written = '';
check(curl_setopt($handle, CURLOPT_WRITEFUNCTION, static function ($curl, string $chunk) use (&$written): int {
    $written .= $chunk;
    return strlen($chunk);
}), 'callback configuration failed');
check(curl_exec($handle) === true && $written === 'fixed-php-body', 'callback body mismatch');
curl_close($handle);
$stream = tmpfile();
check($stream !== false, 'temporary stream init failed');
$fileHandle = curl_init($base . '/fixed');
check($fileHandle !== false, 'stream curl init failed');
check(curl_setopt_array($fileHandle, [CURLOPT_FILE => $stream, CURLOPT_TIMEOUT => 5]), 'stream configuration failed');
check(curl_exec($fileHandle) === true, 'stream transfer failed');
rewind($stream);
check(fread($stream, 5) === 'fixed', 'partial stream mismatch');
fclose($stream);
curl_close($fileHandle);
$failure = curl_init($base . '/error');
check($failure !== false, 'error curl init failed');
check(curl_setopt_array($failure, [CURLOPT_RETURNTRANSFER => true, CURLOPT_TIMEOUT => 5]), 'error configuration failed');
check(curl_exec($failure) === 'fixed-php-body' && curl_errno($failure) === 0 && curl_getinfo($failure, CURLINFO_RESPONSE_CODE) === 500, 'HTTP status was confused with transport failure');
curl_close($failure);
echo 'php=' . PHP_VERSION . '; libcurl=' . curl_version()['version'] . '; cases=11; status=passed' . PHP_EOL;
