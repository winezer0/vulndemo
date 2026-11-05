struct BoxValue { value: String, other: String }
// field_same preserves one object's actual member value.
fn field_same(field_sameInput: String) -> String { let mut holder = BoxValue { value: String::new(), other: String::new() }; holder.value = field_sameInput; let field_sameOut = holder.value; field_sameOut }
// field_distinct keeps separate member identities.
fn field_distinct(field_distinctInput: String) -> String { let mut holder = BoxValue { value: String::new(), other: String::new() }; holder.value = field_distinctInput; let field_distinctOut = holder.other; field_distinctOut }
fn read_box(box_value: BoxValue) -> String { box_value.value }
fn cross_field(cross_fieldInput: String) -> String { let mut holder = BoxValue { value: String::new(), other: String::new() }; holder.value = cross_fieldInput; let cross_fieldOut = read_box(holder); cross_fieldOut }
fn call_isolation(call_isolationInput: String) -> String { let mut tainted = BoxValue { value: String::new(), other: String::new() }; tainted.value = call_isolationInput; read_box(tainted); let clean = BoxValue { value: String::new(), other: String::new() }; let call_isolationOut = read_box(clean); call_isolationOut }
fn cross_overwrite(cross_overwriteInput: String) -> String { let mut holder = BoxValue { value: String::new(), other: String::new() }; holder.value = cross_overwriteInput; holder.value = String::new(); let cross_overwriteOut = read_box(holder); cross_overwriteOut }
fn callback_same(callback_sameInput: String) -> String { let action = |value: String| value; let callback_sameOut = action(callback_sameInput); callback_sameOut }
fn callback_unused(callback_unusedInput: String) -> String { let action = || callback_unusedInput; let callback_unusedOut = String::new(); callback_unusedOut }

fn invoke_callback<F: Fn(String) -> String>(callback: F, value: String) -> String { callback(value) }
fn callback_parameter(callback_parameterInput: String) -> String {
    let selected = |value: String| value;
    let callback_parameterOut = invoke_callback(selected, callback_parameterInput);
    callback_parameterOut
}
fn callback_parameter_isolated(callback_parameter_isolatedInput: String) -> String {
    let selected = |value: String| value;
    invoke_callback(selected, callback_parameter_isolatedInput);
    let callback_parameter_isolatedOut = invoke_callback(selected, String::new());
    callback_parameter_isolatedOut
}
fn array_same(array_sameInput: &str) -> &str { let mut holder = ["", ""]; holder[0] = array_sameInput; let array_sameOut = holder[0]; array_sameOut }
fn array_distinct(array_distinctInput: &str) -> &str { let mut holder = ["", ""]; holder[0] = array_distinctInput; let array_distinctOut = holder[1]; array_distinctOut }
fn array_alias(array_aliasInput: &str) -> &str { let mut holder = ["", ""]; let copy = &mut holder; copy[0] = array_aliasInput; let array_aliasOut = holder[0]; array_aliasOut }
fn array_overwrite(array_overwriteInput: &str) -> &str { let mut holder = ["", ""]; holder[0] = array_overwriteInput; holder[0] = "safe"; let array_overwriteOut = holder[0]; array_overwriteOut }
fn array_initializer(array_initializerInput: &str) -> &str { let holder = [array_initializerInput, "safe"]; let array_initializerOut = holder[0]; array_initializerOut }
fn array_initializer_distinct(array_initializer_distinctInput: &str) -> &str { let holder = [array_initializer_distinctInput, "safe"]; let array_initializer_distinctOut = holder[1]; array_initializer_distinctOut }
fn array_copy(array_copyInput: &str) -> &str { let mut holder = [array_copyInput, "safe"]; let copy = holder; holder[0] = "safe"; let array_copyOut = copy[0]; array_copyOut }
fn array_copy_isolation(array_copy_isolationInput: &str) -> &str { let mut holder = ["safe", "safe"]; let copy = holder; holder[0] = array_copy_isolationInput; let array_copy_isolationOut = copy[0]; array_copy_isolationOut }
fn array_repeat(array_repeatInput: &str) -> &str { let holder = [array_repeatInput; 2]; let array_repeatOut = holder[1]; array_repeatOut }
fn array_unknown_write(array_unknown_writeInput: &str, index: usize) -> &str { let mut holder = ["", ""]; holder[index] = array_unknown_writeInput; let array_unknown_writeOut = holder[0]; array_unknown_writeOut }
fn array_unknown_read(array_unknown_readInput: &str, index: usize) -> &str { let holder = [array_unknown_readInput, "safe"]; let array_unknown_readOut = holder[index]; array_unknown_readOut }
fn echo_value(value: &str) -> &str { value }
fn initializer_call(initializer_callInput: &str) -> &str { let holder = [echo_value(initializer_callInput)]; let initializer_callOut = holder[0]; initializer_callOut }
fn initializer_call_isolation(initializer_call_isolationInput: &str) -> &str { echo_value(initializer_call_isolationInput); let holder = [echo_value("safe")]; let initializer_call_isolationOut = holder[0]; initializer_call_isolationOut }
