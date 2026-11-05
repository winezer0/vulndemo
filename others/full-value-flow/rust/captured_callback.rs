// captured_factory returns a callable over its actual creator argument.
fn captured_factory(value: &'static str) -> impl Fn() -> &'static str { return move || value; }
// callback_capture invokes the returned captured environment.
fn callback_capture(callback_captureInput: &'static str) -> &'static str { let selected = captured_factory(callback_captureInput); let callback_captureOut = selected(); return callback_captureOut; }
// callback_capture_isolation keeps separate factory environments apart.
fn callback_capture_isolation(callback_capture_isolationInput: &'static str) -> &'static str { let first = captured_factory(callback_capture_isolationInput); let second = captured_factory("safe"); first(); let callback_capture_isolationOut = second(); return callback_capture_isolationOut; }
