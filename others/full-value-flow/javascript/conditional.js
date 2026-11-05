function conditional_read(box) { return box.value; }
function reference_keep(reference_keepInput, flag) { let first = {}; let second = {}; first.value = reference_keepInput; if (flag) { first = second; } let reference_keepOut = first.value; return reference_keepOut; }
function reference_new(reference_newInput, flag) { let first = {}; let second = {}; second.value = reference_newInput; if (flag) { first = second; } let reference_newOut = first.value; return reference_newOut; }
function reference_distinct(reference_distinctInput, flag) { let first = {}; let second = {}; first.value = reference_distinctInput; if (flag) { first = second; } let reference_distinctOut = first.other; return reference_distinctOut; }
function reference_call(reference_callInput, flag) { let first = {}; let second = {}; first.value = reference_callInput; if (flag) { first = second; } let reference_callOut = conditional_read(first); return reference_callOut; }
function reference_call_before(reference_call_beforeInput, flag) { let first = {}; let second = {}; second.value = reference_call_beforeInput; let reference_call_beforeOut = conditional_read(first); if (flag) { first = second; } return reference_call_beforeOut; }

function conditional_read_wrap(box) { return conditional_read(box); }
function reference_nested_call(reference_nested_callInput, flag) { let first = {}; let second = {}; first.value = reference_nested_callInput; if (flag) { first = second; } let reference_nested_callOut = conditional_read_wrap(first); return reference_nested_callOut; }
function reference_nested_before(reference_nested_beforeInput, flag) { let first = {}; let second = {}; second.value = reference_nested_beforeInput; let reference_nested_beforeOut = conditional_read_wrap(first); if (flag) { first = second; } return reference_nested_beforeOut; }
