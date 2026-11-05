<?php

$topLevelInput = source_value();
$topLevelBefore = ob_get_contents();
ob_start();
echo $topLevelInput;
$topLevelOut = ob_get_clean();

function php_top_producer() {
    $topProducerInput = source_value();
    echo $topProducerInput;
}

ob_start();
php_top_producer();
$topProducerOut = ob_get_clean();

function php_top_other() {
    ob_start();
    $topLevelOther = ob_get_clean();
    return $topLevelOther;
}

function php_top_anon_producer() {
    $topAnonInput = source_value();
    echo $topAnonInput;
}

ob_start();
$topAnonObject = new class(php_top_anon_producer()) {
    public function __construct($value) {}
};
$topAnonOut = ob_get_clean();

$topCtorInput = source_value();
ob_start();
$topCtorObject = new class($topCtorInput) {
    public function __construct($value) { echo $value; }
};
$topCtorOut = ob_get_clean();

class PhpTopProducer {
    public static function emitStaticValue() { $topStaticInput = source_value(); echo $topStaticInput; }
    public function emitInstanceValue() { $topInstanceInput = source_value(); echo $topInstanceInput; }
}

ob_start();
PhpTopProducer::emitStaticValue();
$topStaticOut = ob_get_clean();
$topInstance = new PhpTopProducer();
ob_start();
$topInstance->emitInstanceValue();
$topInstanceOut = ob_get_clean();

class PhpNullableProducer {
    public function emitNullableValue() { $topNullableInput = source_value(); echo $topNullableInput; }
}
$topNullable = new PhpNullableProducer();
ob_start();
$topNullable?->emitNullableValue();
$topNullableOut = ob_get_clean();

class PhpDynamicProducer {
    public function emitDynamicValue() { $topDynamicInput = source_value(); echo $topDynamicInput; }
}
$topDynamic = new PhpDynamicProducer();
$topMethodName = 'emitDynamicValue';
ob_start();
$topDynamic->$topMethodName();
$topDynamicOut = ob_get_clean();

class PhpDynamicStaticProducer {
    public static function emitDynamicStatic() { $topDynamicStaticInput = source_value(); echo $topDynamicStaticInput; }
}
$topStaticName = 'emitDynamicStatic';
ob_start();
PhpDynamicStaticProducer::$topStaticName();
$topDynamicStaticOut = ob_get_clean();
