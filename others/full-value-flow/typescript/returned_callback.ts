function returned_identity(): (value: string) => string { return (value: string) => value; }
function callback_returned(callback_returnedInput: string): string { const selected = returned_identity(); const callback_returnedOut = selected(callback_returnedInput); return callback_returnedOut; }
function callback_returned_isolation(callback_returned_isolationInput: string): string { const selected = returned_identity(); selected(callback_returned_isolationInput); const callback_returned_isolationOut = selected("safe"); return callback_returned_isolationOut; }
