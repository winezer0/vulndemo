<?php
function projected_pair($input) {
    $value = phpSource();
    return ["safe", $value];
}

function projected_caller($value, $box) {
    [$first, $box->field] = projected_pair($value);
    sink($box->field);
}
