class IgnoringIndex {
    private string stored = "safe";
    public string this[int index] { get { return stored; } set { stored = value; } }
}
class IndexerFlow {
    public static string indexer_unconfirmed(string indexer_unconfirmedInput) {
        var holder = new IgnoringIndex();
        holder[0] = indexer_unconfirmedInput;
        var indexer_unconfirmedOut = holder[1];
        return indexer_unconfirmedOut;
    }
}
