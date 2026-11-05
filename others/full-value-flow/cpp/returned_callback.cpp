#include <functional>
std::function<const char *(const char *)> returned_identity() { return [](const char *value) { return value; }; }
const char *callback_returned(const char *callback_returnedInput) { auto selected = returned_identity(); const char *callback_returnedOut = selected(callback_returnedInput); return callback_returnedOut; }
const char *callback_returned_isolation(const char *callback_returned_isolationInput) { auto selected = returned_identity(); selected(callback_returned_isolationInput); const char *callback_returned_isolationOut = selected("safe"); return callback_returned_isolationOut; }
