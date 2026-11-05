class ConditionalBox:
    pass

def conditional_read(box):
    return box.value

def reference_keep(reference_keepInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    first.value = reference_keepInput
    if flag:
        first = second
    reference_keepOut = first.value
    return reference_keepOut

def reference_new(reference_newInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    second.value = reference_newInput
    if flag:
        first = second
    reference_newOut = first.value
    return reference_newOut

def reference_distinct(reference_distinctInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    first.value = reference_distinctInput
    if flag:
        first = second
    reference_distinctOut = first.other
    return reference_distinctOut

def reference_call(reference_callInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    first.value = reference_callInput
    if flag:
        first = second
    reference_callOut = conditional_read(first)
    return reference_callOut

def reference_call_before(reference_call_beforeInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    second.value = reference_call_beforeInput
    reference_call_beforeOut = conditional_read(first)
    if flag:
        first = second
    return reference_call_beforeOut

def conditional_read_wrap(box):
    return conditional_read(box)
def reference_nested_call(reference_nested_callInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    first.value = reference_nested_callInput
    if flag:
        first = second
    reference_nested_callOut = conditional_read_wrap(first)
    return reference_nested_callOut
def reference_nested_before(reference_nested_beforeInput, flag):
    first = ConditionalBox()
    second = ConditionalBox()
    second.value = reference_nested_beforeInput
    reference_nested_beforeOut = conditional_read_wrap(first)
    if flag:
        first = second
    return reference_nested_beforeOut
