fn rs_sink(value: i32) {}

fn rs_target(first: i32, second: i32) {
    rs_sink(first);
    rs_sink(second);
}

fn rs_caller(input: i32) {
    let value = input;
    rs_target(0, value);
}

fn rs_source() -> i32 { 1 }

fn rs_return_target() -> i32 {
    let value = rs_source();
    return value;
}

fn rs_wrapped_return_target() -> i32 {
    let value = rs_source();
    return ((value));
}

fn rs_wrapped_return_caller() {
    let second = rs_wrapped_return_target();
    rs_sink(second);
}

fn rs_return_caller() {
    let second = rs_return_target();
    rs_sink(second);
}

fn rs_parenthesized_return_caller() {
    let second = ((rs_return_target()));
    rs_sink(second);
}

fn rs_implicit_target() -> i32 {
    let value = rs_source();
    value
}

fn rs_implicit_caller() {
    let second = rs_implicit_target();
    rs_sink(second);
}

struct RSService { value: i32 }

impl RSService {
    fn method(&self, first: i32, second: i32) {
        rs_sink(first);
        rs_sink(second);
        rs_sink(self.value);
    }
}

fn rs_method_caller(input: i32, service: RSService) {
    let value = input;
    service.method(0, value);
}

fn rs_explicit_caller(input: i32, service: RSService) {
    let value = input;
    RSService::method(&service, 0, value);
}

fn rs_receiver_caller(input: RSService) {
    let value = input;
    value.method(0, 0);
}

fn rs_typed_receiver_caller(input: RSService) {
    let value: RSService = input;
    value.method(0, 0);
}

fn rs_block_target() -> i32 {
    let value = rs_source();
    { ((value)) }
}

fn rs_block_caller() {
    let second = rs_block_target();
    rs_sink(second);
}

fn rs_branch_target(ready: bool) -> i32 {
    let value = rs_source();
    if ready { value } else { 0 }
}

fn rs_branch_caller() {
    let second = rs_branch_target(true);
    rs_sink(second);
}

fn rs_statement_target() -> i32 {
    let value = rs_source();
    { value };
    0
}

fn rs_statement_caller() {
    let second = rs_statement_target();
    rs_sink(second);
}
