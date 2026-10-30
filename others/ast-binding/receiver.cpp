class ReceiverCpp {
public:
    void receiverTarget() {
        receiverSink(this);
    }
};
void receiverCaller(ReceiverCpp input) {
    ReceiverCpp value = input;
    value.receiverTarget();
}
