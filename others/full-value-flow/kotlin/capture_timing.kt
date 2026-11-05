// captured_mutation_factory retains a mutable lexical cell.
fun captured_mutation_factory(value: String): () -> String { var current = "safe"; val selected = { -> current }; current = value; return selected; }
// callback_capture_write consumes the mutated environment.
fun callback_capture_write(callback_capture_writeInput: String): String { val selected = captured_mutation_factory(callback_capture_writeInput); val callback_capture_writeOut = selected(); return callback_capture_writeOut; }
// callback_capture_local invokes its actual lexical environment.
fun callback_capture_local(callback_capture_localInput: String): String { val current = callback_capture_localInput; val selected = { -> current }; val callback_capture_localOut = selected(); return callback_capture_localOut; }
// callback_capture_local_isolation preserves independent captured values.
fun callback_capture_local_isolation(callback_capture_local_isolationInput: String): String { val current = callback_capture_local_isolationInput; val first = { -> current }; val safe = "safe"; val second = { -> safe }; first(); val callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
