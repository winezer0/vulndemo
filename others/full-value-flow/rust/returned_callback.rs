fn returned_identity() -> fn(&'static str) -> &'static str { return |value| value; }
fn callback_returned(callback_returnedInput: &'static str) -> &'static str { let selected = returned_identity(); let callback_returnedOut = selected(callback_returnedInput); callback_returnedOut }
fn callback_returned_isolation(callback_returned_isolationInput: &'static str) -> &'static str { let selected = returned_identity(); selected(callback_returned_isolationInput); let callback_returned_isolationOut = selected("safe"); callback_returned_isolationOut }
