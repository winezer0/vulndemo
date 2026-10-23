fn choose(input: i32) -> i32 { input }
pub fn run(input: i32) -> i32 {
    let before = choose(input);
    let choose = |value: i32| value + 1;
    choose(before)
}
