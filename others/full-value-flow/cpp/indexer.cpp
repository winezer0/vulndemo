struct IgnoringIndex {
    const char *stored = "safe";
    const char *&operator[](int index) { return stored; }
};
const char *indexer_unconfirmed(const char *indexer_unconfirmedInput) {
    IgnoringIndex holder = {};
    holder[0] = indexer_unconfirmedInput;
    const char *indexer_unconfirmedOut = holder[1];
    return indexer_unconfirmedOut;
}
