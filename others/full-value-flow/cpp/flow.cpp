struct Box { const char *value = nullptr; const char *other = nullptr; };
// field_same preserves one object's actual member value.
const char *field_same(const char *field_sameInput) { Box holder; holder.value = field_sameInput; const char *field_sameOut = holder.value; return field_sameOut; }
// field_distinct keeps separate member identities.
const char *field_distinct(const char *field_distinctInput) { Box holder; holder.value = field_distinctInput; const char *field_distinctOut = holder.other; return field_distinctOut; }
const char *read_box(Box box) { return box.value; }
const char *cross_field(const char *cross_fieldInput) { Box holder; holder.value = cross_fieldInput; const char *cross_fieldOut = read_box(holder); return cross_fieldOut; }
const char *call_isolation(const char *call_isolationInput) { Box tainted; tainted.value = call_isolationInput; read_box(tainted); Box clean; clean.value = ""; const char *call_isolationOut = read_box(clean); return call_isolationOut; }
const char *cross_overwrite(const char *cross_overwriteInput) { Box holder; holder.value = cross_overwriteInput; holder.value = ""; const char *cross_overwriteOut = read_box(holder); return cross_overwriteOut; }
const char *callback_same(const char *callback_sameInput) { auto action = [](const char *value) { return value; }; const char *callback_sameOut = action(callback_sameInput); return callback_sameOut; }
const char *callback_unused(const char *callback_unusedInput) { auto action = [callback_unusedInput]() { return callback_unusedInput; }; const char *callback_unusedOut = ""; return callback_unusedOut; }

template <typename F> const char *invoke_callback(F callback, const char *value) { return callback(value); }
const char *callback_parameter(const char *callback_parameterInput) {
    auto selected = [](const char *value) { return value; };
    const char *callback_parameterOut = invoke_callback(selected, callback_parameterInput);
    return callback_parameterOut;
}
const char *callback_parameter_isolated(const char *callback_parameter_isolatedInput) {
    auto selected = [](const char *value) { return value; };
    invoke_callback(selected, callback_parameter_isolatedInput);
    const char *callback_parameter_isolatedOut = invoke_callback(selected, "safe");
    return callback_parameter_isolatedOut;
}

const char *array_same(const char *array_sameInput) { const char *holder[2] = {}; holder[0] = array_sameInput; const char *array_sameOut = holder[0]; return array_sameOut; }
const char *array_distinct(const char *array_distinctInput) { const char *holder[2] = {}; holder[0] = array_distinctInput; const char *array_distinctOut = holder[1]; return array_distinctOut; }
const char *array_alias(const char *array_aliasInput) { const char *holder[2] = {}; const char **alias = holder; alias[0] = array_aliasInput; const char *array_aliasOut = holder[0]; return array_aliasOut; }
const char *array_overwrite(const char *array_overwriteInput) { const char *holder[2] = {}; holder[0] = array_overwriteInput; holder[0] = "safe"; const char *array_overwriteOut = holder[0]; return array_overwriteOut; }
const char *array_initializer(const char *array_initializerInput) { const char *holder[2] = {array_initializerInput, "safe"}; const char *array_initializerOut = holder[0]; return array_initializerOut; }
const char *array_initializer_distinct(const char *array_initializer_distinctInput) { const char *holder[2] = {array_initializer_distinctInput, "safe"}; const char *array_initializer_distinctOut = holder[1]; return array_initializer_distinctOut; }
const char *array_unknown_write(const char *array_unknown_writeInput, int index) { const char *holder[2] = {}; holder[index] = array_unknown_writeInput; const char *array_unknown_writeOut = holder[0]; return array_unknown_writeOut; }
const char *array_unknown_read(const char *array_unknown_readInput, int index) { const char *holder[2] = {}; holder[0] = array_unknown_readInput; const char *array_unknown_readOut = holder[index]; return array_unknown_readOut; }
const char *echo_value(const char *value) { return value; }
const char *initializer_call(const char *initializer_callInput) { const char *holder[2] = {echo_value(initializer_callInput), "safe"}; const char *initializer_callOut = holder[0]; return initializer_callOut; }
const char *initializer_call_isolation(const char *initializer_call_isolationInput) { const char *holder[2] = {echo_value(initializer_call_isolationInput), "safe"}; const char *initializer_call_isolationOut = holder[1]; return initializer_call_isolationOut; }
const char *array_reference(const char *array_referenceInput) { const char *holder[2] = {}; auto &alias = holder; alias[0] = array_referenceInput; const char *array_referenceOut = holder[0]; return array_referenceOut; }
const char *field_copy(const char *field_copyInput) { Box holder = {}; holder.value = field_copyInput; Box copy = holder; holder.value = "safe"; const char *field_copyOut = copy.value; return field_copyOut; }
const char *field_copy_isolation(const char *field_copy_isolationInput) { Box holder = {}; Box copy = holder; holder.value = field_copy_isolationInput; const char *field_copy_isolationOut = copy.value; return field_copy_isolationOut; }
const char *field_reference(const char *field_referenceInput) { Box holder = {}; Box &alias = holder; alias.value = field_referenceInput; const char *field_referenceOut = holder.value; return field_referenceOut; }
const char *field_reference_assign(const char *field_reference_assignInput) { Box holder = {}; holder.value = field_reference_assignInput; Box other = {}; other.value = "safe"; Box &alias = holder; alias = other; const char *field_reference_assignOut = holder.value; return field_reference_assignOut; }
const char *array_radix(const char *array_radixInput) { const char *holder[16] = {}; holder[010] = array_radixInput; const char *array_radixOut = holder[8]; return array_radixOut; }
const char *array_radix_distinct(const char *array_radix_distinctInput) { const char *holder[16] = {}; holder[010] = array_radix_distinctInput; const char *array_radix_distinctOut = holder[10]; return array_radix_distinctOut; }
