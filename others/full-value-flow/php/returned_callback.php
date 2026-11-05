<?php
function returned_identity() { return function($value) { return $value; }; }
function callback_returned($callback_returnedInput) { $selected = returned_identity(); $callback_returnedOut = $selected($callback_returnedInput); return $callback_returnedOut; }
function callback_returned_isolation($callback_returned_isolationInput) { $selected = returned_identity(); $selected($callback_returned_isolationInput); $callback_returned_isolationOut = $selected("safe"); return $callback_returned_isolationOut; }
