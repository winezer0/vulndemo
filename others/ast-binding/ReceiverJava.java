class ReceiverJava {
    void receiverTarget() {
        receiverSink(this);
    }
    static void receiverCaller(ReceiverJava input) {
        ReceiverJava value = input;
        value.receiverTarget();
    }

    void receiverTarget(String ignored) {
        receiverSink(this);
    }
}
