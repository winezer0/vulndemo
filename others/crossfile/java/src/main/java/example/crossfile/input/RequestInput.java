package example.crossfile.input;

// RequestInput models request-controlled input entering the command path.
public final class RequestInput {
    private RequestInput() {
    }

    public static String fromRequest(String rawInput) {
        return rawInput;
    }
}
