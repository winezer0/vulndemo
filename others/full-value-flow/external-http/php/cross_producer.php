<?php

function php_cross_file_producer() {
    $crossFileInput = source_value();
    echo $crossFileInput;
}

class PhpCrossMethodProducer {
    public static function emit() {
        $crossMethodInput = source_value();
        echo $crossMethodInput;
    }
}
