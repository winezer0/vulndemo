#include <functional>
// callback_capture_local observes a mutable reference capture before leaving its lifetime.
const char *callback_capture_local(const char *callback_capture_localInput) { const char *current = "safe"; auto selected = [&current]() { return current; }; current = callback_capture_localInput; const char *callback_capture_localOut = selected(); return callback_capture_localOut; }
// callback_capture_local_isolation preserves independent captured values.
const char *callback_capture_local_isolation(const char *callback_capture_local_isolationInput) { auto current = callback_capture_local_isolationInput; auto first = [current]() { return current; }; const char *safe = "safe"; auto second = [safe]() { return safe; }; first(); const char *callback_capture_local_isolationOut = second(); return callback_capture_local_isolationOut; }
