void cpp_sink(int value) {}

void cpp_target(int first, int second) {
    cpp_sink(first);
    cpp_sink(second);
}

void cpp_caller(int input) {
    int value = input;
    cpp_target(0, value);
}

int cpp_source() { return 1; }

int cpp_return_target() {
    int value = cpp_source();
    return value;
}

int cpp_wrapped_return_target() {
    int value = cpp_source();
    return ((value));
}

void cpp_wrapped_return_caller() {
    int second = cpp_wrapped_return_target();
    cpp_sink(second);
}

void cpp_return_caller() {
    int second = cpp_return_target();
    cpp_sink(second);
}

void cpp_parenthesized_return_caller() {
    int second = ((cpp_return_target()));
    cpp_sink(second);
}

class CPPService {
public:
    void method(int first, int second) {
        cpp_sink(first);
        cpp_sink(second);
    }
};

void cpp_method_caller(int input, CPPService service) {
    int value = input;
    service.method(0, value);
}

void cpp_reference_target(int& second) {
    cpp_sink(second);
}

void cpp_reference_caller(int input) {
    int value = input;
    cpp_reference_target(value);
}

void cpp_pointer_target(int (*callback)(int), int second) {
    cpp_sink(second);
}

void cpp_pointer_caller(int input) {
    int value = input;
    cpp_pointer_target(nullptr, value);
}

struct CPPPair { int first; int second; };

CPPPair cpp_pair_target() {
    int value = cpp_source();
    return CPPPair{0, value};
}

void cpp_destructured_caller() {
    auto [first, second] = cpp_pair_target();
    cpp_sink(first);
    cpp_sink(second);
}
