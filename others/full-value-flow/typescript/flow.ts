type Box = {value?: string; other?: string};
// field_same preserves one object's actual member value.
function field_same(field_sameInput: string) { const holder: Box = {}; holder.value = field_sameInput; const field_sameOut = holder.value; return field_sameOut; }
// field_distinct keeps separate member identities.
function field_distinct(field_distinctInput: string) { const holder: Box = {}; holder.value = field_distinctInput; const field_distinctOut = holder.other; return field_distinctOut; }
// read_box supplies a real cross-call member-read boundary.
function read_box(box: Box) { return box.value; }
function object_initializer(object_initializerInput: string) { const holder = {value: object_initializerInput}; const object_initializerOut = holder.value; return object_initializerOut; }
function array_initializer(array_initializerInput: string) { const values = [, array_initializerInput]; const array_initializerOut = values[1]; return array_initializerOut; }
function array_initializer_distinct(array_initializer_distinctInput: string) { const values = [, array_initializer_distinctInput]; const array_initializer_distinctOut = values[0]; return array_initializer_distinctOut; }
function cross_field(cross_fieldInput: string) { const holder: Box = {}; holder.value = cross_fieldInput; const cross_fieldOut = read_box(holder); return cross_fieldOut; }
function call_isolation(call_isolationInput: string) { const holder: Box = {}; holder.value = call_isolationInput; read_box(holder); const clean: Box = {}; clean.value = ""; const call_isolationOut = read_box(clean); return call_isolationOut; }
function cross_overwrite(cross_overwriteInput: string) { const holder: Box = {}; holder.value = cross_overwriteInput; holder.value = ""; const cross_overwriteOut = read_box(holder); return cross_overwriteOut; }
function echo(value: string) { return value; }
function initializer_call(initializer_callInput: string) { const holder = {value: echo(initializer_callInput)}; const initializer_callOut = holder.value; return initializer_callOut; }
function initializer_call_isolation(initializer_call_isolationInput: string) { echo(initializer_call_isolationInput); const holder = {value: echo("")}; const initializer_call_isolationOut = holder.value; return initializer_call_isolationOut; }
function callback_same(callback_sameInput: string) { const action = (value: string) => value; const callback_sameOut = action(callback_sameInput); return callback_sameOut; }
function callback_unused(callback_unusedInput: string) { const action = () => callback_unusedInput; const callback_unusedOut = ""; return callback_unusedOut; }

function invoke_callback(callback: (value: string) => string, value: string) { return callback(value); }
function callback_parameter(callback_parameterInput: string) {
    const selected = (value: string) => value;
    const callback_parameterOut = invoke_callback(selected, callback_parameterInput);
    return callback_parameterOut;
}
function callback_parameter_isolated(callback_parameter_isolatedInput: string) {
    const selected = (value: string) => value;
    invoke_callback(selected, callback_parameter_isolatedInput);
    const callback_parameter_isolatedOut = invoke_callback(selected, "safe");
    return callback_parameter_isolatedOut;
}
