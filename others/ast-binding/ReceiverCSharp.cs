class ReceiverCSharp {
    void ReceiverTarget() {
        ReceiverSink(this);
    }
    static void ReceiverCaller(ReceiverCSharp input) {
        ReceiverCSharp value = input;
        value.ReceiverTarget();
    }
}
