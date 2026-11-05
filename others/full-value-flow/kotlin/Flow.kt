class Box(var value: String = "", var other: String = "")
// field_same preserves one object's actual member value.
fun field_same(field_sameInput: String): String { val holder = Box(); holder.value = field_sameInput; val field_sameOut = holder.value; return field_sameOut }
// field_distinct keeps separate member identities.
fun field_distinct(field_distinctInput: String): String { val holder = Box(); holder.value = field_distinctInput; val field_distinctOut = holder.other; return field_distinctOut }
fun read_box(box: Box): String { return box.value }
fun cross_field(cross_fieldInput: String): String { val holder = Box(); holder.value = cross_fieldInput; val cross_fieldOut = read_box(holder); return cross_fieldOut }
fun call_isolation(call_isolationInput: String): String { val holder = Box(); holder.value = call_isolationInput; read_box(holder); val clean = Box(); clean.value = ""; val call_isolationOut = read_box(clean); return call_isolationOut }
fun cross_overwrite(cross_overwriteInput: String): String { val holder = Box(); holder.value = cross_overwriteInput; holder.value = ""; val cross_overwriteOut = read_box(holder); return cross_overwriteOut }
fun callback_same(callback_sameInput: String): String { val action: (String) -> String = { value -> value }; val callback_sameOut = action(callback_sameInput); return callback_sameOut }
fun callback_unused(callback_unusedInput: String): String { val action = { callback_unusedInput }; val callback_unusedOut = ""; return callback_unusedOut }

fun invoke_callback(callback: (String) -> String, value: String): String { return callback(value) }
fun callback_parameter(callback_parameterInput: String): String {
    val selected: (String) -> String = { value -> value }
    val callback_parameterOut = invoke_callback(selected, callback_parameterInput)
    return callback_parameterOut
}
fun callback_parameter_isolated(callback_parameter_isolatedInput: String): String {
    val selected: (String) -> String = { value -> value }
    invoke_callback(selected, callback_parameter_isolatedInput)
    val callback_parameter_isolatedOut = invoke_callback(selected, "safe")
    return callback_parameter_isolatedOut
}

fun array_same(array_sameInput: String): String { val holder = arrayOf("safe", "safe"); holder[0] = array_sameInput; val array_sameOut = holder[0]; return array_sameOut }
// indexer_distinct_candidate deliberately retains a possible alias until the get/set contract is confirmed.
fun indexer_distinct_candidate(indexer_distinct_candidateInput: String): String { val holder = arrayOf("safe", "safe"); holder[0] = indexer_distinct_candidateInput; val indexer_distinct_candidateOut = holder[1]; return indexer_distinct_candidateOut }
fun array_alias(array_aliasInput: String): String { val holder = arrayOf("safe", "safe"); val alias = holder; alias[0] = array_aliasInput; val array_aliasOut = holder[0]; return array_aliasOut }
fun array_unknown_write(array_unknown_writeInput: String, index: Int): String { val holder = arrayOf("safe", "safe"); holder[index] = array_unknown_writeInput; val array_unknown_writeOut = holder[0]; return array_unknown_writeOut }
fun array_unknown_read(array_unknown_readInput: String, index: Int): String { val holder = arrayOf("safe", "safe"); holder[0] = array_unknown_readInput; val array_unknown_readOut = holder[index]; return array_unknown_readOut }
fun indexer_weak_overwrite(indexer_weak_overwriteInput: String): String { val holder = arrayOf("safe"); holder[0] = indexer_weak_overwriteInput; holder[0] = "safe"; val indexer_weak_overwriteOut = holder[0]; return indexer_weak_overwriteOut }
fun echo_value(value: String): String { return value }
fun indexed_call(indexed_callInput: String): String { val holder = arrayOf("safe", "safe"); holder[0] = echo_value(indexed_callInput); val indexed_callOut = holder[0]; return indexed_callOut }
fun indexer_call_distinct_candidate(indexer_call_distinct_candidateInput: String): String { val holder = arrayOf("safe", "safe"); holder[0] = echo_value(indexer_call_distinct_candidateInput); val indexer_call_distinct_candidateOut = holder[1]; return indexer_call_distinct_candidateOut }
