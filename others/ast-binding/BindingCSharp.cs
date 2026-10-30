class BindingCSharp {
    static void CsSink(string value) {}
    static void CsSink(string[] value) {}

    static void CsTarget(string first, string second) {
        CsSink(first);
        CsSink(second);
    }

    static void CsCaller(string input) {
        string value = input;
        CsTarget("safe", value);
    }

    static void CsNamedCaller(string input) {
        string value = input;
        CsTarget(second: value, first: "safe");
    }

    static void CsRest(string first, params string[] values) {
        CsSink(values);
    }

    static void CsRestCaller(string input) {
        string value = input;
        CsRest("safe", "safe", value);
    }

    static string CsSource() { return "input"; }

    static string CsReturnTarget() {
        string value = CsSource();
        return value;
    }

    static string CsWrappedReturnTarget() {
        string value = CsSource();
        return ((value));
    }

    static void CsWrappedReturnCaller() {
        string second = CsWrappedReturnTarget();
        CsSink(second);
    }

    static void CsReturnCaller() {
        string second = CsReturnTarget();
        CsSink(second);
    }

    static void CsParenthesizedReturnCaller() {
        string second = ((CsReturnTarget()));
        CsSink(second);
    }

    static void CsInputTarget(in string second) {
        CsSink(second);
    }

    static void CsInputCaller(string input) {
        string value = input;
        CsInputTarget(value);
    }

    static void CsReferenceTarget(ref string second) {
        CsSink(second);
    }

    static void CsReferenceCaller(string input) {
        string value = input;
        CsReferenceTarget(ref value);
    }

    static void CsMixedCaller(string input) {
        string value = input;
        CsTarget(first: "safe", value);
    }

    static void CsOtherConflictCaller(string input) {
        string value = input;
        CsTarget("safe", first: "again", second: value);
    }
}
