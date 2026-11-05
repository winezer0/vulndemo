<?php
function curl_dynamic_return_mode($curlDynamicInput, $curlDynamicMode) {
    $handle = curl_init($curlDynamicInput);
    curl_setopt($handle, CURLOPT_RETURNTRANSFER, $curlDynamicMode);
    $curlDynamicOut = curl_exec($handle);
    return $curlDynamicOut;
}

function curl_conditional_return_mode($curlConditionalInput, $curlConditionalMode) {
    $handle = curl_init($curlConditionalInput);
    if ($curlConditionalMode) {
        curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
    }
    $curlConditionalOut = curl_exec($handle);
    return $curlConditionalOut;
}

function curl_dynamic_option_key($curlDynamicKeyInput, $curlDynamicOption) {
    $handle = curl_init($curlDynamicKeyInput);
    curl_setopt($handle, $curlDynamicOption, true);
    $curlDynamicKeyOut = curl_exec($handle);
    return $curlDynamicKeyOut;
}
