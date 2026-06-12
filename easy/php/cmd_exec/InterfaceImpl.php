<?php

require_once __DIR__ . '/../common/VulnChild.php';
require_once __DIR__ . '/../iface/VulnRunner.php';

class InterfaceImpl extends VulnChild implements VulnRunner
{
    public function run(string $cmd): string
    {
        return $this->exec($cmd);
    }
}
