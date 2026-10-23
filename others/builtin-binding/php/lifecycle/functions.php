<?php
namespace Demo {
    function strlen(string $input): int { return 7; }
    function invoke(string $input): int { return strlen($input); }
}
namespace Other {
    function strlen(string $input): int { return 8; }
    function invoke(string $input): int { return strlen($input); }
}
