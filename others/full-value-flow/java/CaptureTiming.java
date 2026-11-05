class CaptureTiming {
    // callback_capture_local invokes its effectively final lexical capture.
    static String callback_capture_local(String callback_capture_localInput) { String current = callback_capture_localInput; java.util.function.Supplier<String> selected = () -> current; String callback_capture_localOut = selected.get(); return callback_capture_localOut; }
    // callback_capture_local_isolation preserves independent captured values.
    static String callback_capture_local_isolation(String callback_capture_local_isolationInput) { String current = callback_capture_local_isolationInput; java.util.function.Supplier<String> first = () -> current; String safe = "safe"; java.util.function.Supplier<String> second = () -> safe; first.get(); String callback_capture_local_isolationOut = second.get(); return callback_capture_local_isolationOut; }
}
