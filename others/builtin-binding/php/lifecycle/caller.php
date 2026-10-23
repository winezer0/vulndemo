<?php
namespace Demo;
use Demo\Alpha as Client;
function run(int $input): int {
    $client = new Client();
    $first = $client->hit($input);
    return $client->hit($first);
}
