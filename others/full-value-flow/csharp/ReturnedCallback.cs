class ReturnedCallbackFlow {
    static System.Func<string, string> returned_identity() { return value => value; }
    static string callback_returned(string callback_returnedInput) { System.Func<string, string> selected = returned_identity(); string callback_returnedOut = selected(callback_returnedInput); return callback_returnedOut; }
    static string callback_returned_isolation(string callback_returned_isolationInput) { System.Func<string, string> selected = returned_identity(); selected(callback_returned_isolationInput); string callback_returned_isolationOut = selected("safe"); return callback_returned_isolationOut; }
}
