<?php
// captured_factory returns a callable over its actual creator argument.
function captured_factory($value) { return function() use ($value) { return $value; }; }
// callback_capture invokes the returned captured environment.
function callback_capture($callback_captureInput) { $selected = captured_factory($callback_captureInput); $callback_captureOut = $selected(); return $callback_captureOut; }
// callback_capture_isolation keeps separate factory environments apart.
function callback_capture_isolation($callback_capture_isolationInput) { $first = captured_factory($callback_capture_isolationInput); $second = captured_factory("safe"); $first(); $callback_capture_isolationOut = $second(); return $callback_capture_isolationOut; }
