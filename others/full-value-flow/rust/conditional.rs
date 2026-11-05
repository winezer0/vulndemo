struct ConditionalBox { value: &'static str, other: &'static str }

// copy_keep checks conditional value snapshots and source isolation.
fn copy_keep(copy_keepInput: &'static str, flag: bool) -> &'static str { let mut first = [""]; let mut second = [""]; first[0] = copy_keepInput; if flag { first = second; }  let copy_keepOut = first[0]; copy_keepOut }

// copy_new checks conditional value snapshots and source isolation.
fn copy_new(copy_newInput: &'static str, flag: bool) -> &'static str { let mut first = [""]; let mut second = [""]; second[0] = copy_newInput; if flag { first = second; }  let copy_newOut = first[0]; copy_newOut }

// copy_isolation checks conditional value snapshots and source isolation.
fn copy_isolation(copy_isolationInput: &'static str, flag: bool) -> &'static str { let mut first = [""]; let mut second = [""]; second[0] = ""; if flag { first = second; } second[0] = copy_isolationInput; let copy_isolationOut = first[0]; copy_isolationOut }
fn conditional_read(box_value: &ConditionalBox) -> &'static str { box_value.value }
fn reference_keep(reference_keepInput: &'static str, flag: bool) -> &'static str { let mut original = ConditionalBox { value: "", other: "" }; let second = ConditionalBox { value: "", other: "" }; original.value = reference_keepInput; let mut first = &original; if flag { first = &second; } let reference_keepOut = first.value; reference_keepOut }
fn reference_new(reference_newInput: &'static str, flag: bool) -> &'static str { let original = ConditionalBox { value: "", other: "" }; let mut second = ConditionalBox { value: "", other: "" }; second.value = reference_newInput; let mut first = &original; if flag { first = &second; } let reference_newOut = first.value; reference_newOut }
fn reference_distinct(reference_distinctInput: &'static str, flag: bool) -> &'static str { let mut original = ConditionalBox { value: "", other: "" }; let second = ConditionalBox { value: "", other: "" }; original.value = reference_distinctInput; let mut first = &original; if flag { first = &second; } let reference_distinctOut = first.other; reference_distinctOut }
fn reference_call(reference_callInput: &'static str, flag: bool) -> &'static str { let mut original = ConditionalBox { value: "", other: "" }; let second = ConditionalBox { value: "", other: "" }; original.value = reference_callInput; let mut first = &original; if flag { first = &second; } let reference_callOut = conditional_read(first); reference_callOut }
fn reference_call_before(reference_call_beforeInput: &'static str, flag: bool) -> &'static str { let original = ConditionalBox { value: "", other: "" }; let mut second = ConditionalBox { value: "", other: "" }; second.value = reference_call_beforeInput; let mut first = &original; let reference_call_beforeOut = conditional_read(first); if flag { first = &second; } reference_call_beforeOut }

fn conditional_read_wrap(box_value: &ConditionalBox) -> &'static str { conditional_read(box_value) }
fn reference_nested_call(reference_nested_callInput: &'static str, flag: bool) -> &'static str { let mut original = ConditionalBox { value: "", other: "" }; let second = ConditionalBox { value: "", other: "" }; original.value = reference_nested_callInput; let mut first = &original; if flag { first = &second; } let reference_nested_callOut = conditional_read_wrap(first); reference_nested_callOut }
fn reference_nested_before(reference_nested_beforeInput: &'static str, flag: bool) -> &'static str { let original = ConditionalBox { value: "", other: "" }; let mut second = ConditionalBox { value: "", other: "" }; second.value = reference_nested_beforeInput; let mut first = &original; let reference_nested_beforeOut = conditional_read_wrap(first); if flag { first = &second; } reference_nested_beforeOut }
