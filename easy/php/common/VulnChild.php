<?php

require_once __DIR__ . '/VulnParent.php';

class VulnChild extends VulnParent
{
    public function run(string $cmd): string
    {
        return $this->exec($cmd);
    }
}
