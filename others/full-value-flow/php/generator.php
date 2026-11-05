<?php
// generator_payload yields a real value to an explicit Generator consumer.
function generator_payload($value): Generator { yield $value; }
// async_schedule consumes its own generator payload.
function async_schedule($async_scheduleInput) {
    $pending = generator_payload($async_scheduleInput);
    $async_scheduleOut = $pending->current();
    return $async_scheduleOut;
}
// async_schedule_isolation never consumes the independent tainted generator.
function async_schedule_isolation($async_schedule_isolationInput) {
    $first = generator_payload($async_schedule_isolationInput);
    $second = generator_payload("safe");
    $async_schedule_isolationOut = $second->current();
    return $async_schedule_isolationOut;
}
// async_schedule_overwrite consumes the replacement generator's own safe payload.
function async_schedule_overwrite($async_schedule_overwriteInput) {
    $pending = generator_payload($async_schedule_overwriteInput);
    $pending = generator_payload("safe");
    $async_schedule_overwriteOut = $pending->current();
    return $async_schedule_overwriteOut;
}
