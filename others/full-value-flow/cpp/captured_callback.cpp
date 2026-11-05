#include <functional>
// captured_factory returns a callable over its actual creator argument.
std::function<const char *()> captured_factory(const char *value) { return [value]() { return value; }; }
// callback_capture invokes the returned captured environment.
const char *callback_capture(const char *callback_captureInput) { auto selected = captured_factory(callback_captureInput); const char *callback_captureOut = selected(); return callback_captureOut; }
// callback_capture_isolation keeps separate factory environments apart.
const char *callback_capture_isolation(const char *callback_capture_isolationInput) { auto first = captured_factory(callback_capture_isolationInput); auto second = captured_factory("safe"); first(); const char *callback_capture_isolationOut = second(); return callback_capture_isolationOut; }
