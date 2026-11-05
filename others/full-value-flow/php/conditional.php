<?php
class ConditionalBox { public $value = ""; public $other = ""; }
function copy_keep($copy_keepInput, $flag) { $first = ["key" => $copy_keepInput]; $second = ["key" => ""]; if ($flag) { $first = $second; } $copy_keepOut = $first["key"]; return $copy_keepOut; }
function copy_new($copy_newInput, $flag) { $first = ["key" => ""]; $second = ["key" => $copy_newInput]; if ($flag) { $first = $second; } $copy_newOut = $first["key"]; return $copy_newOut; }
function copy_isolation($copy_isolationInput, $flag) { $first = ["key" => ""]; $second = ["key" => ""]; if ($flag) { $first = $second; } $second["key"] = $copy_isolationInput; $copy_isolationOut = $first["key"]; return $copy_isolationOut; }
function conditional_read($box) { return $box->value; }
function reference_keep($reference_keepInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $first->value = $reference_keepInput; if ($flag) { $first = $second; } $reference_keepOut = $first->value; return $reference_keepOut; }
function reference_new($reference_newInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $second->value = $reference_newInput; if ($flag) { $first = $second; } $reference_newOut = $first->value; return $reference_newOut; }
function reference_distinct($reference_distinctInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $first->value = $reference_distinctInput; if ($flag) { $first = $second; } $reference_distinctOut = $first->other; return $reference_distinctOut; }
function reference_call($reference_callInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $first->value = $reference_callInput; if ($flag) { $first = $second; } $reference_callOut = conditional_read($first); return $reference_callOut; }
function reference_call_before($reference_call_beforeInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $second->value = $reference_call_beforeInput; $reference_call_beforeOut = conditional_read($first); if ($flag) { $first = $second; } return $reference_call_beforeOut; }

function conditional_read_wrap($box) { return conditional_read($box); }
function reference_nested_call($reference_nested_callInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $first->value = $reference_nested_callInput; if ($flag) { $first = $second; } $reference_nested_callOut = conditional_read_wrap($first); return $reference_nested_callOut; }
function reference_nested_before($reference_nested_beforeInput, $flag) { $first = new ConditionalBox(); $second = new ConditionalBox(); $second->value = $reference_nested_beforeInput; $reference_nested_beforeOut = conditional_read_wrap($first); if ($flag) { $first = $second; } return $reference_nested_beforeOut; }
