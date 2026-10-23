<?php
namespace Demo;
function capture(Alpha $client, int $input): int {
    $action = function(int $value) use ($client, $input): int {
        return $client->hit($value + $input);
    };
    $arrow = fn(int $value): int => $client->hit($value + $input);
    return $action($input) + $arrow($input);
}
