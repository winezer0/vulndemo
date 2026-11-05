class Flow {
    static class Box { String value; String other; }
    // field_same preserves one object's actual member value.
    static String field_same(String field_sameInput) { Box holder = new Box(); holder.value = field_sameInput; String field_sameOut = holder.value; return field_sameOut; }
    // field_distinct keeps separate member identities.
    static String field_distinct(String field_distinctInput) { Box holder = new Box(); holder.value = field_distinctInput; String field_distinctOut = holder.other; return field_distinctOut; }
    static String read_box(Box box) { return box.value; }
    static String cross_field(String cross_fieldInput) { Box holder = new Box(); holder.value = cross_fieldInput; String cross_fieldOut = read_box(holder); return cross_fieldOut; }
    static String call_isolation(String call_isolationInput) { Box tainted = new Box(); tainted.value = call_isolationInput; read_box(tainted); Box clean = new Box(); clean.value = ""; String call_isolationOut = read_box(clean); return call_isolationOut; }
    static String cross_overwrite(String cross_overwriteInput) { Box holder = new Box(); holder.value = cross_overwriteInput; holder.value = ""; String cross_overwriteOut = read_box(holder); return cross_overwriteOut; }
    static String callback_same(String callback_sameInput) { java.util.function.Function<String, String> action = value -> value; String callback_sameOut = action.apply(callback_sameInput); return callback_sameOut; }
    static String callback_unused(String callback_unusedInput) { java.util.function.Supplier<String> action = () -> callback_unusedInput; String callback_unusedOut = ""; return callback_unusedOut; }

    static String invoke_callback(java.util.function.Function<String, String> callback, String value) { return callback.apply(value); }
    static String callback_parameter(String callback_parameterInput) {
        java.util.function.Function<String, String> selected = value -> value;
        String callback_parameterOut = invoke_callback(selected, callback_parameterInput);
        return callback_parameterOut;
    }
    static String callback_parameter_isolated(String callback_parameter_isolatedInput) {
        java.util.function.Function<String, String> selected = value -> value;
        invoke_callback(selected, callback_parameter_isolatedInput);
        String callback_parameter_isolatedOut = invoke_callback(selected, "safe");
        return callback_parameter_isolatedOut;
    }
    static String array_same(String array_sameInput) { String[] holder = new String[2]; holder[0] = array_sameInput; String array_sameOut = holder[0]; return array_sameOut; }
    static String array_distinct(String array_distinctInput) { String[] holder = new String[2]; holder[0] = array_distinctInput; String array_distinctOut = holder[1]; return array_distinctOut; }
    static String array_alias(String array_aliasInput) { String[] holder = new String[2]; String[] copy = holder; copy[0] = array_aliasInput; String array_aliasOut = holder[0]; return array_aliasOut; }
    static String array_overwrite(String array_overwriteInput) { String[] holder = new String[2]; holder[0] = array_overwriteInput; holder[0] = "safe"; String array_overwriteOut = holder[0]; return array_overwriteOut; }
    static String array_initializer(String array_initializerInput) { String[] holder = new String[]{array_initializerInput, "safe"}; String array_initializerOut = holder[0]; return array_initializerOut; }
    static String array_initializer_distinct(String array_initializer_distinctInput) { String[] holder = {array_initializer_distinctInput, "safe"}; String array_initializer_distinctOut = holder[1]; return array_initializer_distinctOut; }
    static String array_unknown_write(String array_unknown_writeInput, int index) { String[] holder = new String[2]; holder[index] = array_unknown_writeInput; String array_unknown_writeOut = holder[0]; return array_unknown_writeOut; }
    static String array_unknown_read(String array_unknown_readInput, int index) { String[] holder = new String[2]; holder[0] = array_unknown_readInput; String array_unknown_readOut = holder[index]; return array_unknown_readOut; }
    static String echo_value(String value) { return value; }
    static String initializer_call(String initializer_callInput) { String[] holder = {echo_value(initializer_callInput)}; String initializer_callOut = holder[0]; return initializer_callOut; }
    static String initializer_call_isolation(String initializer_call_isolationInput) { echo_value(initializer_call_isolationInput); String[] holder = {echo_value("safe")}; String initializer_call_isolationOut = holder[0]; return initializer_call_isolationOut; }
}
