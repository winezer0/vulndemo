namespace { namespace detail { void hiddenContract() {} } }
class OuterContract {
    friend class OuterFriend;
    class InnerContract {
        friend class InnerFriend;
    public:
        InnerContract() {}
        ~InnerContract() {}
        void runContract() {}
    };
protected:
    int protectedContract;
};
int unknownContract(Unknown box, int input) {
    int first = box.consumeContract(input);
    return box.consumeContract(input);
}
