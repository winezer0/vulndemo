<?php
function phpSink($value) {}

function phpTarget($first, $second) {
    phpSink($first);
    phpSink($second);
}

function phpCaller($input) {
    $value = $input;
    phpTarget("safe", $value);
}

function phpNamedCaller($input) {
    $value = $input;
    phpTarget(second: $value, first: "safe");
}

function phpRest($first, ...$values) {
    phpSink($values);
}

function phpRestCaller($input) {
    $value = $input;
    phpRest("safe", "safe", $value);
}

function phpSpreadCaller($input) {
    $value = $input;
    phpRest(...$value);
}

function phpSource() { return "input"; }

function phpReturnTarget() {
    $value = phpSource();
    return $value;
}

function phpWrappedReturnTarget() {
    $value = phpSource();
    return (($value));
}

function phpWrappedReturnCaller() {
    $second = phpWrappedReturnTarget();
    phpSink($second);
}

function phpReturnCaller() {
    $second = phpReturnTarget();
    phpSink($second);
}

function phpParenthesizedReturnCaller() {
    $second = ((phpReturnTarget()));
    phpSink($second);
}

class PHPService {
    function method($first, $second) {
        phpSink($first);
        phpSink($second);
    }
}

function phpMethodCaller($input) {
    $value = $input;
    $service = new PHPService();
    $service->method("safe", $value);
}

function phpFixedSpreadCaller($input) {
    $value = $input;
    phpTarget(...["safe", $value]);
}

function phpKeyedSpreadCaller($input) {
    $value = $input;
    phpTarget(...["second" => $value, "first" => "safe"]);
}

function phpReferenceTarget(&$second) {
    phpSink($second);
}

function phpReferenceCaller($input) {
    $value = $input;
    phpReferenceTarget($value);
}
