<?php
function read_box($box) { return $box->value; }
function cross_field($cross_fieldInput) { $holder = new Box(); $holder->value = $cross_fieldInput; $cross_fieldOut = read_box($holder); return $cross_fieldOut; }
class Box { public $value = ""; public $other = ""; }
// field_same preserves one object's actual member value.
function field_same($field_sameInput) { $holder = new Box(); $holder->value = $field_sameInput; $field_sameOut = $holder->value; return $field_sameOut; }
// field_distinct keeps separate member identities.
function field_distinct($field_distinctInput) { $holder = new Box(); $holder->value = $field_distinctInput; $field_distinctOut = $holder->other; return $field_distinctOut; }
function call_isolation($call_isolationInput) { $holder = new Box(); $holder->value = $call_isolationInput; read_box($holder); $clean = new Box(); $clean->value = ""; $call_isolationOut = read_box($clean); return $call_isolationOut; }
function cross_overwrite($cross_overwriteInput) { $holder = new Box(); $holder->value = $cross_overwriteInput; $holder->value = ""; $cross_overwriteOut = read_box($holder); return $cross_overwriteOut; }
function object_initializer($object_initializerInput) { $holder = ["" => $object_initializerInput]; $object_initializerOut = $holder[""]; return $object_initializerOut; }
function array_initializer($array_initializerInput) { $holder = ["", $array_initializerInput]; $array_initializerOut = $holder[1]; return $array_initializerOut; }
function array_initializer_distinct($array_initializer_distinctInput) { $holder = ["", $array_initializer_distinctInput]; $array_initializer_distinctOut = $holder[0]; return $array_initializer_distinctOut; }
function echo_value($value) { return $value; }
function initializer_call($initializer_callInput) { $holder = ["value" => echo_value($initializer_callInput)]; $initializer_callOut = $holder["value"]; return $initializer_callOut; }
function initializer_call_isolation($initializer_call_isolationInput) { echo_value($initializer_call_isolationInput); $holder = ["value" => echo_value("")]; $initializer_call_isolationOut = $holder["value"]; return $initializer_call_isolationOut; }
function array_copy($array_copyInput) { $holder = ["value" => $array_copyInput]; $copy = $holder; $holder["value"] = ""; $array_copyOut = $copy["value"]; return $array_copyOut; }
function array_copy_isolation($array_copy_isolationInput) { $holder = ["value" => ""]; $copy = $holder; $holder["value"] = $array_copy_isolationInput; $array_copy_isolationOut = $copy["value"]; return $array_copy_isolationOut; }
function array_key_coercion($array_key_coercionInput) { $holder = [1 => $array_key_coercionInput]; $array_key_coercionOut = $holder["1"]; return $array_key_coercionOut; }
function array_key_distinct($array_key_distinctInput) { $holder = [1 => $array_key_distinctInput, "01" => ""]; $array_key_distinctOut = $holder["01"]; return $array_key_distinctOut; }
function callback_same($callback_sameInput) { $action = fn($value) => $value; $callback_sameOut = $action($callback_sameInput); return $callback_sameOut; }
function callback_unused($callback_unusedInput) { $action = fn() => $callback_unusedInput; $callback_unusedOut = ""; return $callback_unusedOut; }

function invoke_callback(callable $callback, $value) { return $callback($value); }
function callback_parameter($callback_parameterInput) {
    $selected = fn($value) => $value;
    $callback_parameterOut = invoke_callback($selected, $callback_parameterInput);
    return $callback_parameterOut;
}
function callback_parameter_isolated($callback_parameter_isolatedInput) {
    $selected = fn($value) => $value;
    invoke_callback($selected, $callback_parameter_isolatedInput);
    $callback_parameter_isolatedOut = invoke_callback($selected, "safe");
    return $callback_parameter_isolatedOut;
}
