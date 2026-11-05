<?php
// captured_mutation_factory retains a reference capture with post-creation mutation.
function captured_mutation_factory($value) { $current = "safe"; $selected = function() use (&$current) { return $current; }; $current = $value; return $selected; }
// callback_capture_write consumes the mutated captured environment.
function callback_capture_write($callback_capture_writeInput) { $selected = captured_mutation_factory($callback_capture_writeInput); $callback_capture_writeOut = $selected(); return $callback_capture_writeOut; }
// callback_capture_local invokes its actual lexical environment.
function callback_capture_local($callback_capture_localInput) { $current = $callback_capture_localInput; $selected = function() use ($current) { return $current; }; $callback_capture_localOut = $selected(); return $callback_capture_localOut; }
// callback_capture_local_isolation preserves independent captured values.
function callback_capture_local_isolation($callback_capture_local_isolationInput) { $current = $callback_capture_local_isolationInput; $first = function() use ($current) { return $current; }; $safe = "safe"; $second = function() use ($safe) { return $safe; }; $first(); $callback_capture_local_isolationOut = $second(); return $callback_capture_local_isolationOut; }
