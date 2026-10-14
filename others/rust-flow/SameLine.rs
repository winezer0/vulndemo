fn sink(value: i32) {}
fn run(a: i32, b: i32) { sink(a); sink(a + b); }
struct Alpha { value: i32 }
struct Beta { value: i32 }
fn first() -> i32 { let item: Alpha = todo!(); item.value } fn second() -> i32 { let item: Beta = todo!(); item.value }
impl Alpha { fn ping(&self) {} } impl Beta { fn ping(&self) {} }
fn first_call() { let item: Alpha = todo!(); item.ping(); } fn second_call() { let item: Beta = todo!(); item.ping(); }
fn choose(flag: bool, left: i32, right: i32) -> i32 { return if flag { left } else { right }; }
fn pick(flag: bool, left: i32, right: i32) -> i32 { return match flag { true => left, false => right }; }
fn shadow() { let item: Alpha = todo!(); { let item: Beta = todo!(); let _ = item.value; } let _ = item.value; }
fn echo(pair: (i32, String)) -> (i32, String) { pair }
struct Pair { left: i32, right: i32 } fn pair(left: i32, right: i32) -> Pair { return Pair { left, right }; }
fn config_first() { std::env::var("TOKEN"); std::env::var("TOKEN"); } fn config_second() { std::env::var("TOKEN"); }
fn write_box(mut box_value: Pair, input: i32) { box_value.left = input; }
fn negate(value: i32) -> i32 { return -value; }
fn dereference(value: &i32) -> i32 { return *value; }
fn copy_pair(mut value: Pair) { value.left = value.right; }
