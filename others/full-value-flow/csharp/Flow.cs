class Flow {
    class Box { public string value; public string other; }
    // field_same preserves one object's actual member value.
    static string field_same(string field_sameInput) { var holder = new Box(); holder.value = field_sameInput; string field_sameOut = holder.value; return field_sameOut; }
    // field_distinct keeps separate member identities.
    static string field_distinct(string field_distinctInput) { var holder = new Box(); holder.value = field_distinctInput; string field_distinctOut = holder.other; return field_distinctOut; }
    static string read_box(Box box) { return box.value; }
    static string cross_field(string cross_fieldInput) { var holder = new Box(); holder.value = cross_fieldInput; string cross_fieldOut = read_box(holder); return cross_fieldOut; }
    static string call_isolation(string call_isolationInput) { var tainted = new Box(); tainted.value = call_isolationInput; read_box(tainted); var clean = new Box(); clean.value = ""; string call_isolationOut = read_box(clean); return call_isolationOut; }
    static string cross_overwrite(string cross_overwriteInput) { var holder = new Box(); holder.value = cross_overwriteInput; holder.value = ""; string cross_overwriteOut = read_box(holder); return cross_overwriteOut; }
    static string callback_same(string callback_sameInput) { System.Func<string, string> action = value => value; string callback_sameOut = action(callback_sameInput); return callback_sameOut; }
    static string callback_unused(string callback_unusedInput) { System.Func<string> action = () => callback_unusedInput; string callback_unusedOut = ""; return callback_unusedOut; }

    static string invoke_callback(System.Func<string, string> callback, string value) { return callback(value); }
    static string callback_parameter(string callback_parameterInput) {
        System.Func<string, string> selected = value => value;
        string callback_parameterOut = invoke_callback(selected, callback_parameterInput);
        return callback_parameterOut;
    }
    static string callback_parameter_isolated(string callback_parameter_isolatedInput) {
        System.Func<string, string> selected = value => value;
        invoke_callback(selected, callback_parameter_isolatedInput);
        string callback_parameter_isolatedOut = invoke_callback(selected, "safe");
        return callback_parameter_isolatedOut;
    }
    static string array_same(string array_sameInput) { string[] holder = new string[2]; holder[0] = array_sameInput; string array_sameOut = holder[0]; return array_sameOut; }
    static string array_distinct(string array_distinctInput) { string[] holder = new string[2]; holder[0] = array_distinctInput; string array_distinctOut = holder[1]; return array_distinctOut; }
    static string array_alias(string array_aliasInput) { string[] holder = new string[2]; string[] copy = holder; copy[0] = array_aliasInput; string array_aliasOut = holder[0]; return array_aliasOut; }
    static string array_overwrite(string array_overwriteInput) { string[] holder = new string[2]; holder[0] = array_overwriteInput; holder[0] = "safe"; string array_overwriteOut = holder[0]; return array_overwriteOut; }
    static string array_unknown_write(string array_unknown_writeInput, int index) { string[] holder = new string[2]; holder[index] = array_unknown_writeInput; string array_unknown_writeOut = holder[0]; return array_unknown_writeOut; }
    static string array_unknown_read(string array_unknown_readInput, int index) { string[] holder = new string[2]; holder[0] = array_unknown_readInput; string array_unknown_readOut = holder[index]; return array_unknown_readOut; }
    static string array_initializer(string array_initializerInput) { string[] holder = new string[]{array_initializerInput, "safe"}; string array_initializerOut = holder[0]; return array_initializerOut; }
    static string array_initializer_distinct(string array_initializer_distinctInput) { string[] holder = {array_initializer_distinctInput, "safe"}; string array_initializer_distinctOut = holder[1]; return array_initializer_distinctOut; }
    static string echo_value(string value) { return value; }
    static string initializer_call(string initializer_callInput) { string[] holder = {echo_value(initializer_callInput)}; string initializer_callOut = holder[0]; return initializer_callOut; }
    static string initializer_call_isolation(string initializer_call_isolationInput) { echo_value(initializer_call_isolationInput); string[] holder = {echo_value("safe")}; string initializer_call_isolationOut = holder[0]; return initializer_call_isolationOut; }
}
