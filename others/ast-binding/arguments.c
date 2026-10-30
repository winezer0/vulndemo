void c_sink(int value) {}

void c_target(int first, int second) {
    c_sink(first);
    c_sink(second);
}

void c_caller(int input) {
    int value = input;
    c_target(0, value);
}

int c_source(void) { return 1; }

int c_return_target(void) {
    int value = c_source();
    return value;
}

int c_wrapped_return_target(void) {
    int value = c_source();
    return ((value));
}

void c_wrapped_return_caller(void) {
    int second = c_wrapped_return_target();
    c_sink(second);
}

void c_return_caller(void) {
    int second = c_return_target();
    c_sink(second);
}

void c_parenthesized_return_caller(void) {
    int second = ((c_return_target()));
    c_sink(second);
}

void c_pointer_target(int (*callback)(int), int second) {
    c_sink(second);
}

void c_pointer_caller(int input) {
    int value = input;
    c_pointer_target(0, value);
}
