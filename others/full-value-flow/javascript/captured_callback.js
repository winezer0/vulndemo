// captured_factory returns a callable over its actual creator argument.
function captured_factory(value) { return () => value; }
// callback_capture invokes the returned captured environment.
function callback_capture(callback_captureInput) { const selected = captured_factory(callback_captureInput); const callback_captureOut = selected(); return callback_captureOut; }
// callback_capture_isolation keeps separate factory environments apart.
function callback_capture_isolation(callback_capture_isolationInput) { const first = captured_factory(callback_capture_isolationInput); const second = captured_factory("safe"); first(); const callback_capture_isolationOut = second(); return callback_capture_isolationOut; }
