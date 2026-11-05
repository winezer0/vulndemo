<?php

require_once __DIR__ . '/cross_producer.php';
$crossIndirectBefore = ob_get_contents();
$crossIndirectFn = 'php_cross_file_producer';
ob_start();
call_user_func($crossIndirectFn);
$crossIndirectOutput = ob_get_clean();
