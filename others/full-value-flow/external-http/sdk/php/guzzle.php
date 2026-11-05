<?php
$vendor = getenv('HTTP_SDK_PHP_VENDOR');
if (!is_string($vendor) || $vendor === '') {
    throw new RuntimeException('SDK vendor directory is required');
}
require $vendor . '/autoload.php';
require __DIR__ . '/../../php/clients.php';

// check rejects mismatched Guzzle behavior without external test dependencies.
function check(bool $condition, string $message): void {
    if (!$condition) {
        throw new RuntimeException($message);
    }
}

$base = getenv('HTTP_SDK_BASE_URL');
check(is_string($base) && $base !== '', 'loopback server URL is required');
$client = new GuzzleHttp\Client(['base_uri' => $base, 'timeout' => 5]);
$response = $client->get('/fixed');
check($response->getStatusCode() === 201, 'status mismatch');
check((string)$response->getBody() === 'fixed-php-body', 'body mismatch');
$stream = $client->get('/fixed')->getBody();
check($stream->read(5) === 'fixed', 'partial read mismatch');
check($stream->getContents() === '-php-body', 'remaining range mismatch');
$asynchronous = $client->requestAsync('GET', '/fixed')->wait();
check((string)$asynchronous->getBody() === 'fixed-php-body', 'promise body mismatch');
check(guzzle_async_body($base . '/fixed') === 'fixed-php-body', 'fixture promise body mismatch');
check(guzzle_custom_body($base . '/fixed', 'local-fixture-body') === 'local-fixture-body', 'fixture custom handler mismatch');
check(guzzle_rebound_body($base . '/fixed', 'old-body', 'rebound-body') === 'rebound-body', 'fixture rebound capture mismatch');
check(guzzle_reference_body($base . '/fixed', 'reference-body') === 'reference-body', 'fixture reference capture mismatch');
check(guzzle_aliased_body($base . '/fixed', 'alias-body') === 'alias-body', 'fixture aliased capture mismatch');
check(guzzle_options_body($base . '/fixed', 'options-body') === 'options-body', 'fixture stored options mismatch');
check(guzzle_incremental_body($base . '/fixed', 'incremental-body') === 'incremental-body', 'fixture incremental options mismatch');
$failed = false;
try {
    $client->get('/error');
} catch (GuzzleHttp\Exception\RequestException $error) {
    $failed = $error->getResponse() !== null && $error->getResponse()->getStatusCode() === 500;
}
check($failed, 'HTTP failure response mismatch');
$custom = new GuzzleHttp\Client(['handler' => static function ($request, array $options) {
    return GuzzleHttp\Promise\Create::promiseFor(new GuzzleHttp\Psr7\Response(202, [], 'local-guzzle-body'));
}]);
check((string)$custom->get('https://fixture.invalid/unused')->getBody() === 'local-guzzle-body', 'custom handler mismatch');
echo 'php=' . PHP_VERSION . '; guzzle=' . Composer\InstalledVersions::getPrettyVersion('guzzlehttp/guzzle') . '; cases=14; status=passed' . PHP_EOL;
