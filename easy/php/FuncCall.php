<?php

require_once __DIR__ . '/cmd_exec/InterfaceImpl.php';
require_once __DIR__ . '/common/VulnChild.php';
require_once __DIR__ . '/iface/VulnRunner.php';

class FuncCall
{
    public function runByChild(string $cmd): string
    {
        return (new VulnChild())->run($cmd);
    }

    public function runByInterface(string $cmd): string
    {
        $runner = new InterfaceImpl();
        return $runner->run($cmd);
    }
}
