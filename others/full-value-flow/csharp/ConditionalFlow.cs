class ConditionalFlow {
    class ConditionalBox { public string value = ""; public string other = ""; }
    static string conditional_read(ConditionalBox box) { return box.value; }
    static string reference_keep(string reference_keepInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); first.value = reference_keepInput; if (flag) { first = second; } var reference_keepOut = first.value; return reference_keepOut; }
    static string reference_new(string reference_newInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); second.value = reference_newInput; if (flag) { first = second; } var reference_newOut = first.value; return reference_newOut; }
    static string reference_distinct(string reference_distinctInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); first.value = reference_distinctInput; if (flag) { first = second; } var reference_distinctOut = first.other; return reference_distinctOut; }
    static string reference_call(string reference_callInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); first.value = reference_callInput; if (flag) { first = second; } var reference_callOut = conditional_read(first); return reference_callOut; }
    static string reference_call_before(string reference_call_beforeInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); second.value = reference_call_beforeInput; var reference_call_beforeOut = conditional_read(first); if (flag) { first = second; } return reference_call_beforeOut; }
    static string conditional_read_wrap(ConditionalBox box) { return conditional_read(box); }
    static string reference_nested_call(string reference_nested_callInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); first.value = reference_nested_callInput; if (flag) { first = second; } var reference_nested_callOut = conditional_read_wrap(first); return reference_nested_callOut; }
    static string reference_nested_before(string reference_nested_beforeInput, bool flag) { var first = new ConditionalBox(); var second = new ConditionalBox(); second.value = reference_nested_beforeInput; var reference_nested_beforeOut = conditional_read_wrap(first); if (flag) { first = second; } return reference_nested_beforeOut; }
}
