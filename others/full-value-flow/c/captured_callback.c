struct CapturedContext { const char *value; };
typedef const char *(*CapturedInvoke)(struct CapturedContext *);

// captured_read reads only the actual invocation's explicit environment.
const char *captured_read(struct CapturedContext *context) { return context->value; }
// captured_factory returns the actual function pointer used with an explicit environment.
CapturedInvoke captured_factory(void) { return captured_read; }
// callback_capture passes its actual context to the returned callable.
const char *callback_capture(const char *callback_captureInput) {
    struct CapturedContext context = {callback_captureInput};
    CapturedInvoke selected = captured_factory();
    const char *callback_captureOut = selected(&context);
    return callback_captureOut;
}
// callback_capture_isolation keeps separate explicit environments apart.
const char *callback_capture_isolation(const char *callback_capture_isolationInput) {
    struct CapturedContext first = {callback_capture_isolationInput};
    struct CapturedContext second = {"safe"};
    CapturedInvoke selected = captured_factory();
    selected(&first);
    const char *callback_capture_isolationOut = selected(&second);
    return callback_capture_isolationOut;
}
