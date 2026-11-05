fun returned_identity(): (String) -> String { return { value -> value } }
fun callback_returned(callback_returnedInput: String): String { val selected = returned_identity(); val callback_returnedOut = selected(callback_returnedInput); return callback_returnedOut }
fun callback_returned_isolation(callback_returned_isolationInput: String): String { val selected = returned_identity(); selected(callback_returned_isolationInput); val callback_returned_isolationOut = selected("safe"); return callback_returned_isolationOut }
