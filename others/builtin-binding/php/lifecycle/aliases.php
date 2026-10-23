<?php
namespace Demo;
use Demo\Alpha as Client;
use function Demo\choose as pick;
function alias(Client $client, int $input): int {
    return $client->hit(pick($input));
}
