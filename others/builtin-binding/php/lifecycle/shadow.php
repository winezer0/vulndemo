<?php
namespace Demo;
function choose(int $input): int { return $input; }
function shadow(int $input): int {
    $choose = fn(int $value): int => $value + 1;
    $first = choose($input);
    return $choose($first);
}
