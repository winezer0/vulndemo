class CaptureTiming {
    // captured_mutation_factory retains a mutable lexical cell.
    static System.Func<string> captured_mutation_factory(string value) { string current = "safe"; System.Func<string> selected = () => current; current = value; return selected; }
    // callback_capture_write consumes the mutated environment.
    static string callback_capture_write(string callback_capture_writeInput) { System.Func<string> selected = captured_mutation_factory(callback_capture_writeInput); string callback_capture_writeOut = selected(); return callback_capture_writeOut; }
    // callback_capture_local invokes its actual lexical environment.
    static string callback_capture_local(string callback_capture_localInput) { string current = callback_capture_localInput; System.Func<string> selected = () => current; string callback_capture_localOut = selected(); return callback_capture_localOut; }
    // callback_capture_local_isolation preserves independent captured values.
    static string callback_capture_local_isolation(string callback_capture_local_isolationInput) { string current = callback_capture_local_isolationInput; System.Func<string> first = () => current; string safe = "safe"; System.Func<string> second = () => safe; first(); string callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
}
