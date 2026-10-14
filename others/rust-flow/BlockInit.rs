fn sink(value: i32) -> i32 { value }
fn block_init(input: i32) -> i32 {
    let value = { let shadow = input; shadow };
    return value;
}
fn shadow_init(input: i32) {
    let value = input;
    let value = sink(value);
}
