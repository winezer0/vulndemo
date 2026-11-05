class CapturedCallbackFlow {
    // captured_factory returns a callable over its actual creator argument.
    static System.Func<string> captured_factory(string value) { return () => value; }
    // callback_capture invokes the returned captured environment.
    static string callback_capture(string callback_captureInput) { System.Func<string> selected = captured_factory(callback_captureInput); string callback_captureOut = selected(); return callback_captureOut; }
    // callback_capture_isolation keeps separate factory environments apart.
    static string callback_capture_isolation(string callback_capture_isolationInput) { System.Func<string> first = captured_factory(callback_capture_isolationInput); System.Func<string> second = captured_factory("safe"); first(); string callback_capture_isolationOut = second(); return callback_capture_isolationOut; }
}
