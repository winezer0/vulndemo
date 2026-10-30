class Projections {
    static (string, string) Pair(string input) {
        string value = CsSource();
        return ("safe", value);
    }

    static void Caller(string value, Box box) {
        string first;
        (first, box.Field) = Pair(value);
        Sink(box.Field);
    }
}
