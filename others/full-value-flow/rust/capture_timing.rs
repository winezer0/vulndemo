// callback_capture_local invokes an owned lexical capture.
fn callback_capture_local(callback_capture_localInput: &'static str) -> &'static str { let current = callback_capture_localInput; let selected = move || current; let callback_capture_localOut = selected(); return callback_capture_localOut; }
// callback_capture_local_isolation preserves independent moved captures.
fn callback_capture_local_isolation(callback_capture_local_isolationInput: &'static str) -> &'static str { let current = callback_capture_local_isolationInput; let first = move || current; let safe = "safe"; let second = move || safe; first(); let callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
