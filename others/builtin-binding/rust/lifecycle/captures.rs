use crate::types::Alpha;
pub fn run(client: Alpha, input: i32) -> i32 {
    let action = move |value: i32| client.hit(value + input);
    action(input)
}
