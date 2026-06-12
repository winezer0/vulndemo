<?php

class VulnParent
{
    public function exec(string $cmd): string
    {
        return shell_exec($cmd) ?? '';
    }
}
