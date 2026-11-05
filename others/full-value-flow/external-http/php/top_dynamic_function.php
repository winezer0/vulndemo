<?php

function php_dynamic_function_producer() {
    $topDynamicFunctionInput = source_value();
    echo $topDynamicFunctionInput;
}

$topDynamicFunctionName = 'php_dynamic_function_producer';
ob_start();
$topDynamicFunctionName();
$topDynamicFunctionOut = ob_get_clean();
