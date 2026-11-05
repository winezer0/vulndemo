<?php
// fiber_unstarted has no execution boundary before final-return consumption.
function fiber_unstarted($fiber_unstartedInput) {
    $produce = function () use ($fiber_unstartedInput) { return $fiber_unstartedInput; };
    $pending = new Fiber($produce);
    $fiber_unstartedOut = $pending->getReturn();
    return $fiber_unstartedOut;
}
// fiber_start_result must not confuse the callback's final return with start's suspension result.
function fiber_start_result($fiber_start_resultInput) {
    $produce = function () use ($fiber_start_resultInput) { return $fiber_start_resultInput; };
    $pending = new Fiber($produce);
    $fiber_start_resultOut = $pending->start();
    return $fiber_start_resultOut;
}
// fiber_overwrite consumes a replacement instance with its own callback and lifecycle.
function fiber_overwrite($fiber_overwriteInput) {
    $produce = function () use ($fiber_overwriteInput) { return $fiber_overwriteInput; };
    $pending = new Fiber($produce);
    $pending->start();
    $safe = function () { return "safe"; };
    $pending = new Fiber($safe);
    $pending->start();
    $fiber_overwriteOut = $pending->getReturn();
    return $fiber_overwriteOut;
}
// async_callback consumes the final return of its own started Fiber.
function async_callback($async_callbackInput) {
    $produce = function () use ($async_callbackInput) { return $async_callbackInput; };
    $pending = new Fiber($produce);
    $pending->start();
    $async_callbackOut = $pending->getReturn();
    return $async_callbackOut;
}
// async_callback_isolation consumes only the independent safe Fiber.
function async_callback_isolation($async_callback_isolationInput) {
    $tainted = function () use ($async_callback_isolationInput) { return $async_callback_isolationInput; };
    $safe = function () { return "safe"; };
    $first = new Fiber($tainted);
    $second = new Fiber($safe);
    $first->start();
    $second->start();
    $async_callback_isolationOut = $second->getReturn();
    return $async_callback_isolationOut;
}
