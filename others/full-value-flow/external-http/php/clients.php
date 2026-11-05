<?php
use GuzzleHttp\Client;

function curl_body(string $curlInput) {
    $handle = curl_init($curlInput);
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
    $curlOut = curl_exec($handle);
    curl_close($handle);
    return $curlOut;
}

function curl_option_sequence(string $curlOptionInput) {
    $handle = curl_init($curlOptionInput);
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
    $curlOptionFirstOut = curl_exec($handle);
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, false);
    ob_start();
    $curlOptionFalseOut = curl_exec($handle);
    $curlOptionEcho = ob_get_clean();
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
    $curlOptionLastOut = curl_exec($handle);
    curl_close($handle);
    return [$curlOptionFirstOut, $curlOptionFalseOut, $curlOptionEcho, $curlOptionLastOut];
}

function curl_array_options(string $curlArrayInput, bool $curlArrayTransfer) {
    $handle = curl_init('https://example.invalid/');
    $other = curl_init('https://other.invalid/');
    curl_setopt_array($handle, [
        CURLOPT_URL => $curlArrayInput,
        CURLOPT_RETURNTRANSFER => $curlArrayTransfer,
    ]);
    $curlArrayOut = curl_exec($handle);
    $curlArrayOther = curl_exec($other);
    return [$curlArrayOut, $curlArrayOther];
}

function php_summary_history($summarySeed, $summaryFirst, $summarySecond, $summaryBranch) {
    $handle = make_handle($summarySeed);
    $alias = $handle;
    $other = make_handle('independent');
    $summaryBefore = read_handle($handle);
    update_handle($handle, $summaryFirst);
    if ($summaryBranch) {
        update_handle($alias, $summarySecond);
    }
    $summaryOut = read_handle($handle);
    $summaryOther = read_handle($other);
    return [$summaryBefore, $summaryOut, $summaryOther];
}

function php_summary_stress($summaryStressInput) {
    $handle = make_handle($summaryStressInput);
    update_handle($handle, 'a');
    update_handle($handle, 'b');
    update_handle($handle, 'c');
    update_handle($handle, 'd');
    update_handle($handle, 'e');
    update_handle($handle, 'f');
    update_handle($handle, 'g');
    update_handle($handle, 'h');
    $summaryStressOut = read_handle($handle);
    return $summaryStressOut;
}

function php_summary_loop($summaryLoopSeed, $summaryLoopUpdate, $summaryLoopCount) {
    $handle = make_handle($summaryLoopSeed);
    $summaryLoopBefore = read_handle($handle);
    while ($summaryLoopCount) {
        $summaryLoopRead = read_handle($handle);
        update_handle($handle, $summaryLoopUpdate);
        $summaryLoopCount--;
    }
    $summaryLoopOut = read_handle($handle);
    return [$summaryLoopBefore, $summaryLoopRead, $summaryLoopOut];
}

function php_fresh_loop($freshLoopUpdate, $freshLoopCount) {
    $handle = make_handle();
    for ($i = 0; $i < $freshLoopCount; $i++) {
        $freshLoopRead = read_handle($handle);
        update_handle($handle, $freshLoopUpdate);
    }
    return $freshLoopRead;
}

function php_foreach_loop($foreachLoopUpdate, $foreachLoopItems) {
    $handle = make_handle();
    foreach ($foreachLoopItems as $item) {
        $foreachLoopRead = read_handle($handle);
        update_handle($handle, $foreachLoopUpdate);
    }
    return $foreachLoopRead;
}

function php_do_loop($doLoopUpdate, $doLoopCount) {
    $handle = make_handle();
    do {
        $doLoopRead = read_handle($handle);
        update_handle($handle, $doLoopUpdate);
    } while ($doLoopCount--);
    return $doLoopRead;
}

function php_receiver_loop($receiverLoopUpdate, $receiverLoopCount) {
    $object = make_object();
    $other = make_object();
    $receiverLoopBefore = $other->read();
    while ($receiverLoopCount--) {
        $receiverLoopRead = $object->read();
        $object->configure($receiverLoopUpdate);
    }
    return [$receiverLoopRead, $receiverLoopBefore];
}

function php_loop_alias($aliasLoopUpdate, $aliasLoopCount, $aliasLoopSelect) {
    $handle = make_handle();
    $other = make_handle();
    $alias = $other;
    if ($aliasLoopSelect) {
        $alias = $handle;
    }
    while ($aliasLoopCount--) {
        $aliasLoopRead = read_handle($handle);
        update_handle($alias, $aliasLoopUpdate);
    }
    return $aliasLoopRead;
}

function php_output_loop($outputLoopInput, $outputLoopCount) {
    $outputLoopBefore = ob_get_clean();
    while ($outputLoopCount--) {
        $outputLoopRead = ob_get_clean();
        ob_start();
        unknown_emitter($outputLoopInput);
    }
    return [$outputLoopBefore, $outputLoopRead];
}

function php_capture_constructs($captureEchoInput, $capturePrintInput) {
    ob_start();
    echo $captureEchoInput;
    print $capturePrintInput;
    $captureConstructsOut = ob_get_clean();
    return $captureConstructsOut;
}

function php_capture_contents($captureContentsInput) {
    ob_start();
    echo $captureContentsInput;
    $captureContentsOut = ob_get_contents();
    ob_end_clean();
    return $captureContentsOut;
}

function php_capture_flush($captureFlushInput) {
    ob_start();
    echo $captureFlushInput;
    $captureFlushOut = ob_get_flush();
    return $captureFlushOut;
}

function php_capture_nested($captureNestedInput) {
    ob_start();
    ob_start();
    echo $captureNestedInput;
    ob_get_flush();
    $captureNestedOut = ob_get_clean();
    return $captureNestedOut;
}

function php_capture_producer() {
    $captureCrossInput = source_value();
    echo $captureCrossInput;
}

function php_capture_producer_wrapper() {
    php_capture_producer();
}

function php_capture_cross() {
    $captureCrossBefore = ob_get_clean();
    ob_start();
    php_capture_producer_wrapper();
    $captureCrossOut = ob_get_clean();
    return [$captureCrossBefore, $captureCrossOut];
}

function php_capture_unrelated() {
    ob_start();
    $captureCrossOther = ob_get_clean();
    return $captureCrossOther;
}

function php_capture_closure() {
    $captureClosureFn = function () {
        $captureClosureInput = source_value();
        print $captureClosureInput;
    };
    ob_start();
    $captureClosureFn();
    $captureClosureOut = ob_get_clean();
    return $captureClosureOut;
}

function guzzle_body(string $guzzleInput) {
    $client = new Client();
    $response = $client->request('GET', $guzzleInput);
    $stream = $response->getBody();
    $guzzleOut = $stream->getContents();
    return $guzzleOut;
}

// guzzle_async_body waits for the response before reading its stream.
function guzzle_async_body(string $guzzleAsyncInput) {
    $client = new Client();
    $pending = $client->requestAsync('GET', $guzzleAsyncInput);
    $response = $pending->wait();
    $stream = $response->getBody();
    $guzzleAsyncOut = $stream->getContents();
    return $guzzleAsyncOut;
}

// guzzle_custom_body reads a response produced by a user-supplied handler.
function guzzle_custom_body(string $guzzleCustomInput, string $guzzleLocalInput) {
    $client = new Client(['handler' => function ($request, array $options) use ($guzzleLocalInput) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $guzzleLocalInput));
    }]);
    $response = $client->request('GET', $guzzleCustomInput);
    $stream = $response->getBody();
    $guzzleCustomOut = $stream->getContents();
    return $guzzleCustomOut;
}

// guzzle_rebound_body captures the current value after an earlier assignment.
function guzzle_rebound_body(string $guzzleVersionUrl, string $guzzleVersionOld, string $guzzleVersionInput) {
    $body = $guzzleVersionOld;
    $body = $guzzleVersionInput;
    $client = new Client(['handler' => function ($request, array $options) use ($body) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $body));
    }]);
    $response = $client->request('GET', $guzzleVersionUrl);
    $stream = $response->getBody();
    $guzzleVersionOut = $stream->getContents();
    return $guzzleVersionOut;
}

// guzzle_reference_body reads a captured reference after it has been rebound.
function guzzle_reference_body(string $guzzleRefUrl, string $guzzleRefInput) {
    $body = 'old-body';
    $client = new Client(['handler' => function ($request, array $options) use (&$body) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $body));
    }]);
    $body = $guzzleRefInput;
    $response = $client->request('GET', $guzzleRefUrl);
    $stream = $response->getBody();
    $guzzleRefOut = $stream->getContents();
    return $guzzleRefOut;
}

// guzzle_aliased_body passes a closure through a local alias before constructing the client.
function guzzle_aliased_body(string $guzzleAliasUrl, string $guzzleAliasInput) {
    $handler = function ($request, array $options) use ($guzzleAliasInput) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $guzzleAliasInput));
    };
    $alias = $handler;
    $client = new Client(['handler' => $alias]);
    $response = $client->request('GET', $guzzleAliasUrl);
    $stream = $response->getBody();
    $guzzleAliasOut = $stream->getContents();
    return $guzzleAliasOut;
}

// guzzle_options_body stores the closure inside options before constructing the client.
function guzzle_options_body(string $guzzleOptionsUrl, string $guzzleOptionsInput) {
    $options = ['handler' => function ($request, array $requestOptions) use ($guzzleOptionsInput) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $guzzleOptionsInput));
    }];
    $client = new Client($options);
    $response = $client->request('GET', $guzzleOptionsUrl);
    $stream = $response->getBody();
    $guzzleOptionsOut = $stream->getContents();
    return $guzzleOptionsOut;
}

// guzzle_incremental_body assigns a handler after creating its configuration array.
function guzzle_incremental_body(string $guzzleIncrementalUrl, string $guzzleIncrementalInput) {
    $options = [];
    $options['handler'] = function ($request, array $requestOptions) use ($guzzleIncrementalInput) {
        return \GuzzleHttp\Promise\Create::promiseFor(new \GuzzleHttp\Psr7\Response(200, [], $guzzleIncrementalInput));
    };
    $client = new Client($options);
    $response = $client->request('GET', $guzzleIncrementalUrl);
    $stream = $response->getBody();
    $guzzleIncrementalOut = $stream->getContents();
    return $guzzleIncrementalOut;
}

function curl_handles(string $curlFirstInput, string $curlSecondInput, bool $curlTransferOption) {
    $first = curl_init($curlFirstInput);
    curl_setopt($first, CURLOPT_RETURNTRANSFER, $curlTransferOption);
    $curlFirstOut = curl_exec($first);
    $second = curl_init($curlSecondInput);
    curl_setopt($second, CURLOPT_RETURNTRANSFER, true);
    $curlSecondOut = curl_exec($second);
    return [$curlFirstOut, $curlSecondOut];
}

function curl_reused(string $curlReuseInput, string $curlReuseNext) {
    $handle = curl_init($curlReuseInput);
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
    $curlReuseFirstOut = curl_exec($handle);
    curl_setopt($handle, CURLOPT_URL, $curlReuseNext);
    $curlReuseNextOut = curl_exec($handle);
    return [$curlReuseFirstOut, $curlReuseNextOut];
}

function curl_parameter($curlParamHandle, string $curlParamNext) {
    curl_setopt($curlParamHandle, CURLOPT_URL, $curlParamNext);
    $curlParamOut = curl_exec($curlParamHandle);
    return $curlParamOut;
}

class FixtureCarrier {
    private string $value = '';
    function configure(string $value): void { $this->value = $value; }
    function read(): string { return $this->value; }
}

function php_receiver($phpReceiverObject, string $phpReceiverInput) {
    $phpReceiverBefore = $phpReceiverObject->read();
    $phpReceiverObject->configure($phpReceiverInput);
    $phpReceiverOut = $phpReceiverObject->read();
    return $phpReceiverOut;
}

function curl_callback(string $curlCallbackInput) {
    $handle = curl_init($curlCallbackInput);
    $written = '';
    curl_setopt($handle, CURLOPT_WRITEFUNCTION, function ($curl, string $curlCallbackChunk) use (&$written): int {
        $written = $curlCallbackChunk;
        return strlen($curlCallbackChunk);
    });
    curl_exec($handle);
    $curlCallbackOut = $written;
    return $curlCallbackOut;
}

function curl_array_literal_true(string $curlArrayLiteralTrueInput) {
    $handle = curl_init($curlArrayLiteralTrueInput);
    curl_setopt_array($handle, [CURLOPT_RETURNTRANSFER => true]);
    $curlArrayLiteralTrueOut = curl_exec($handle);
    curl_close($handle);
    return $curlArrayLiteralTrueOut;
}

function curl_array_literal_false(string $curlArrayLiteralFalseInput) {
    $handle = curl_init($curlArrayLiteralFalseInput);
    curl_setopt_array($handle, array(CURLOPT_RETURNTRANSFER => false));
    ob_start();
    $curlArrayLiteralFalseResult = curl_exec($handle);
    $curlArrayLiteralFalseOut = ob_get_clean();
    curl_close($handle);
    return [$curlArrayLiteralFalseResult, $curlArrayLiteralFalseOut];
}

function curl_array_multi_option_true(string $curlArrayMultiTrueInput) {
    $handle = curl_init($curlArrayMultiTrueInput);
    curl_setopt_array($handle, [CURLOPT_RETURNTRANSFER => true, CURLOPT_TIMEOUT => 5]);
    $curlArrayMultiTrueOut = curl_exec($handle);
    curl_close($handle);
    return $curlArrayMultiTrueOut;
}

function curl_array_multi_option_false(string $curlArrayMultiFalseInput) {
    $handle = curl_init($curlArrayMultiFalseInput);
    curl_setopt_array($handle, [CURLOPT_RETURNTRANSFER => false, CURLOPT_TIMEOUT => 5]);
    ob_start();
    $curlArrayMultiFalseResult = curl_exec($handle);
    $curlArrayMultiFalseOut = ob_get_clean();
    curl_close($handle);
    return [$curlArrayMultiFalseResult, $curlArrayMultiFalseOut];
}

function curl_array_unknown_file_option(string $curlArrayUnknownInput, $curlArrayOutput) {
    $handle = curl_init($curlArrayUnknownInput);
    curl_setopt_array($handle, [CURLOPT_RETURNTRANSFER => true, CURLOPT_FILE => $curlArrayOutput]);
    $curlArrayUnknownOut = curl_exec($handle);
    curl_close($handle);
    return $curlArrayUnknownOut;
}
