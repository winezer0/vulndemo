class JavaCaller {
    void invoke(JavaDispatch service, String value) {
        service.consume(value);
    }
}
