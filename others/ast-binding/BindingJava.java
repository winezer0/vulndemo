class BindingJava {
    String field;
    static void javaSink(String value) {}
    static void javaSink(String[] value) {}

    static void javaTarget(String first, String second) {
        javaSink(first);
        javaSink(second);
    }

    static void javaCaller(String input) {
        String value = input;
        javaTarget("safe", value);
    }

    static void javaRest(String first, String... values) {
        javaSink(values);
    }

    static void javaRestCaller(String input) {
        String value = input;
        javaRest("safe", "safe", value);
    }

    static String javaSource() { return "input"; }

    static String javaReturnTarget() {
        String value = javaSource();
        return value;
    }

    static String javaWrappedReturnTarget() {
        String value = javaSource();
        return ((value));
    }

    static void javaWrappedReturnCaller() {
        String second = javaWrappedReturnTarget();
        javaSink(second);
    }

    static void javaReturnCaller() {
        String second = javaReturnTarget();
        javaSink(second);
    }

    static void javaParenthesizedReturnCaller() {
        String second = ((javaReturnTarget()));
        javaSink(second);
    }

    void javaMethodTarget(String second) {
        javaSink(second);
        javaSink(this.field);
    }

    static void javaMethodCaller(String input, BindingJava service) {
        String value = input;
        service.javaMethodTarget(value);
    }

    static void javaReceiverCaller(BindingJava input) {
        BindingJava value = input;
        value.javaMethodTarget("safe");
    }

    interface JavaDispatch { void consume(String second); }

    static class JavaFirst implements JavaDispatch {
        public void consume(String second) { javaSink(second); }
    }

    static class JavaSecond implements JavaDispatch {
        public void consume(String second) { javaSink(second); }
    }

    static void javaDynamicCaller(String input, JavaDispatch service) {
        String value = input;
        service.consume(value);
    }
}
