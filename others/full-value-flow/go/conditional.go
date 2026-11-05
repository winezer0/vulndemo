package fullvalueflow

type ConditionalBox struct{ value, other string }

// copy_keep checks conditional value snapshots and source isolation.
func copy_keep(copy_keepInput string, flag bool) string {
 first := [1]string{}
 second := [1]string{}
 first[0] = copy_keepInput
 if flag { first = second }
  copy_keepOut := first[0]
 return copy_keepOut
}

// copy_new checks conditional value snapshots and source isolation.
func copy_new(copy_newInput string, flag bool) string {
 first := [1]string{}
 second := [1]string{}
 second[0] = copy_newInput
 if flag { first = second }
  copy_newOut := first[0]
 return copy_newOut
}

// copy_isolation checks conditional value snapshots and source isolation.
func copy_isolation(copy_isolationInput string, flag bool) string {
 first := [1]string{}
 second := [1]string{}
 second[0] = ""
 if flag { first = second }
 second[0] = copy_isolationInput
 copy_isolationOut := first[0]
 return copy_isolationOut
}

// conditional_read returns one field from the actual pointer argument.
func conditional_read(box *ConditionalBox) string { return box.value }

// conditional_read_wrap forwards the actual object through a second native call.
func conditional_read_wrap(box *ConditionalBox) string { return conditional_read(box) }

// reference_nested_call retains branch targets through two native parameter bindings.
func reference_nested_call(reference_nested_callInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	original.value = reference_nested_callInput
	first := &original
	if flag {
		first = &second
	}
	reference_nested_callOut := conditional_read_wrap(first)
	return reference_nested_callOut
}

// reference_keep retains the previous pointer when the branch does not execute.
func reference_keep(reference_keepInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	original.value = reference_keepInput
	first := &original
	if flag {
		first = &second
	}
	reference_keepOut := first.value
	return reference_keepOut
}

// reference_new retains the pointer selected by the branch.
func reference_new(reference_newInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	second.value = reference_newInput
	first := &original
	if flag {
		first = &second
	}
	reference_newOut := first.value
	return reference_newOut
}

// reference_distinct isolates a different field through a conditional pointer.
func reference_distinct(reference_distinctInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	original.value = reference_distinctInput
	first := &original
	if flag {
		first = &second
	}
	reference_distinctOut := first.other
	return reference_distinctOut
}

// reference_call passes the possible pointer targets to a real callee.
func reference_call(reference_callInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	original.value = reference_callInput
	first := &original
	if flag {
		first = &second
	}
	reference_callOut := conditional_read(first)
	return reference_callOut
}

// reference_call_before excludes pointer bindings made after the call.
func reference_call_before(reference_call_beforeInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	second.value = reference_call_beforeInput
	first := &original
	reference_call_beforeOut := conditional_read(first)
	if flag {
		first = &second
	}
	return reference_call_beforeOut
}

func reference_nested_before(reference_nested_beforeInput string, flag bool) string {
	original := ConditionalBox{}
	second := ConditionalBox{}
	second.value = reference_nested_beforeInput
	first := &original
	reference_nested_beforeOut := conditional_read_wrap(first)
	if flag {
		first = &second
	}
	return reference_nested_beforeOut
}
