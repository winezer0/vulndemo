// callback_capture_local invokes a closure in its owning scope.
function callback_capture_local(callback_capture_localInput) { const current = callback_capture_localInput; const selected = () => current; const callback_capture_localOut = selected(); return callback_capture_localOut; }
// callback_capture_local_isolation preserves independent captured values.
function callback_capture_local_isolation(callback_capture_local_isolationInput) { const current = callback_capture_local_isolationInput; const first = () => current; const safe = "safe"; const second = () => safe; first(); const callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
// captured_mutation_factory retains a mutable lexical cell across callable creation.
function captured_mutation_factory(value) { let current = "safe"; const selected = () => current; current = value; return selected; }
// callback_capture_write observes a write made after callable creation.
function callback_capture_write(callback_capture_writeInput) { const selected = captured_mutation_factory(callback_capture_writeInput); const callback_capture_writeOut = selected(); return callback_capture_writeOut; }
