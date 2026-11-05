<?php

require_once __DIR__ . '/cross_producer.php';
require_once __DIR__ . '/cross_wrapper.php';
require_once __DIR__ . '/cross_wrapper_outer.php';
$crossFileBefore = ob_get_contents();
$crossMethodObject = new PhpCrossMethodProducer();
$crossMethodName = 'emit';
ob_start();
$crossMethodObject->$crossMethodName();
$crossDynamicInstanceOutput = ob_get_clean();
ob_start();
PhpCrossMethodProducer::$crossMethodName();
$crossDynamicMethodOutput = ob_get_clean();
ob_start();
PhpCrossMethodProducer::emit();
$crossMethodOutput = ob_get_clean();
$crossFn = 'php_cross_file_producer';
ob_start();
$crossFn();
$crossDynamicOutput = ob_get_clean();
ob_start();
php_cross_wrapper_outer();
$crossNestedOutput = ob_get_clean();
ob_start();
php_cross_wrapper();
$crossWrappedOutput = ob_get_clean();
ob_start();
php_cross_file_producer();
$crossFileOutput = ob_get_clean();

$crossLocalInput = source_value();
$crossLocalCopy = $crossLocalInput;
ob_start();
echo $crossLocalCopy;
$crossLocalOutput = ob_get_clean();

function php_cross_other() {
    ob_start();
    $crossFileOther = ob_get_clean();
    return $crossFileOther;
}
