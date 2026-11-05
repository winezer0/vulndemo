// captured_mutation_factory retains a mutable lexical cell.
function captured_mutation_factory(value: string): () => string { let current = "safe"; const selected = () => current; current = value; return selected; }
// callback_capture_write observes a post-creation write.
function callback_capture_write(callback_capture_writeInput: string): string { const selected = captured_mutation_factory(callback_capture_writeInput); const callback_capture_writeOut = selected(); return callback_capture_writeOut; }
// callback_capture_local invokes its actual lexical environment.
function callback_capture_local(callback_capture_localInput: string): string { const current = callback_capture_localInput; const selected = () => current; const callback_capture_localOut = selected(); return callback_capture_localOut; }
// callback_capture_local_isolation preserves independent captured values.
function callback_capture_local_isolation(callback_capture_local_isolationInput: string): string { const current = callback_capture_local_isolationInput; const first = () => current; const safe = "safe"; const second = () => safe; first(); const callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
