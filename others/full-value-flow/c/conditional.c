struct ConditionalBox { const char *value; const char *other; };

// copy_keep checks conditional value snapshots and source isolation.
const char *copy_keep(const char *copy_keepInput, int flag) { struct ConditionalBox first = {0}; struct ConditionalBox second = {0}; first.value = copy_keepInput; if (flag) { first = second; }  const char *copy_keepOut = first.value; return copy_keepOut; }

// copy_new checks conditional value snapshots and source isolation.
const char *copy_new(const char *copy_newInput, int flag) { struct ConditionalBox first = {0}; struct ConditionalBox second = {0}; second.value = copy_newInput; if (flag) { first = second; }  const char *copy_newOut = first.value; return copy_newOut; }

// copy_isolation checks conditional value snapshots and source isolation.
const char *copy_isolation(const char *copy_isolationInput, int flag) { struct ConditionalBox first = {0}; struct ConditionalBox second = {0}; second.value = ""; if (flag) { first = second; } second.value = copy_isolationInput; const char *copy_isolationOut = first.value; return copy_isolationOut; }
const char *conditional_read(struct ConditionalBox *box) { return box->value; }
const char *reference_keep(const char *reference_keepInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; original.value = reference_keepInput; struct ConditionalBox *first = &original; if (flag) { first = &second; } const char *reference_keepOut = first->value;  return reference_keepOut; }
const char *reference_new(const char *reference_newInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; second.value = reference_newInput; struct ConditionalBox *first = &original; if (flag) { first = &second; } const char *reference_newOut = first->value;  return reference_newOut; }
const char *reference_distinct(const char *reference_distinctInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; original.value = reference_distinctInput; struct ConditionalBox *first = &original; if (flag) { first = &second; } const char *reference_distinctOut = first->other;  return reference_distinctOut; }
const char *reference_call(const char *reference_callInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; original.value = reference_callInput; struct ConditionalBox *first = &original; if (flag) { first = &second; } const char *reference_callOut = conditional_read(first);  return reference_callOut; }
const char *reference_call_before(const char *reference_call_beforeInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; second.value = reference_call_beforeInput; struct ConditionalBox *first = &original;  const char *reference_call_beforeOut = conditional_read(first); if (flag) { first = &second; } return reference_call_beforeOut; }

const char *conditional_read_wrap(struct ConditionalBox *box) { return conditional_read(box); }
const char *reference_nested_call(const char *reference_nested_callInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; original.value = reference_nested_callInput; struct ConditionalBox *first = &original; if (flag) { first = &second; } const char *reference_nested_callOut = conditional_read_wrap(first);  return reference_nested_callOut; }
const char *reference_nested_before(const char *reference_nested_beforeInput, int flag) { struct ConditionalBox original = {0}; struct ConditionalBox second = {0}; second.value = reference_nested_beforeInput; struct ConditionalBox *first = &original;  const char *reference_nested_beforeOut = conditional_read_wrap(first); if (flag) { first = &second; } return reference_nested_beforeOut; }
