typedef const char *(*ReturnedCallback)(const char *);
const char *returned_callback_value(const char *value) { return value; }
ReturnedCallback returned_identity(void) { return returned_callback_value; }
const char *callback_returned(const char *callback_returnedInput) { ReturnedCallback selected = returned_identity(); const char *callback_returnedOut = selected(callback_returnedInput); return callback_returnedOut; }
const char *callback_returned_isolation(const char *callback_returned_isolationInput) { ReturnedCallback selected = returned_identity(); selected(callback_returned_isolationInput); const char *callback_returned_isolationOut = selected("safe"); return callback_returned_isolationOut; }
