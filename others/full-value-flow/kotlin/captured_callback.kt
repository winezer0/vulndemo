// captured_factory returns a callable over its actual creator argument.
fun captured_factory(value: String): () -> String { return { -> value } }
// callback_capture invokes the returned captured environment.
fun callback_capture(callback_captureInput: String): String { val selected = captured_factory(callback_captureInput); val callback_captureOut = selected(); return callback_captureOut }
// callback_capture_isolation keeps separate factory environments apart.
fun callback_capture_isolation(callback_capture_isolationInput: String): String { val first = captured_factory(callback_capture_isolationInput); val second = captured_factory("safe"); first(); val callback_capture_isolationOut = second(); return callback_capture_isolationOut }
