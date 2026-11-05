class CapturedCallback {
    // captured_factory returns a callable over its actual creator argument.
    static java.util.function.Supplier<String> captured_factory(String value) { return () -> value; }
    // callback_capture invokes the returned captured environment.
    static String callback_capture(String callback_captureInput) { java.util.function.Supplier<String> selected = captured_factory(callback_captureInput); String callback_captureOut = selected.get(); return callback_captureOut; }
    // callback_capture_isolation keeps separate factory environments apart.
    static String callback_capture_isolation(String callback_capture_isolationInput) { java.util.function.Supplier<String> first = captured_factory(callback_capture_isolationInput); java.util.function.Supplier<String> second = captured_factory("safe"); first.get(); String callback_capture_isolationOut = second.get(); return callback_capture_isolationOut; }
}
