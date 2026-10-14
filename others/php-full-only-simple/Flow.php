<?php
function sink($value) {}
function run($input) {
    $local = $input;
    sink($local);
}
