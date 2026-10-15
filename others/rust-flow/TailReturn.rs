fn run(value: i32) -> i32 { value }
fn choose(flag: bool, left: i32, right: i32) -> i32 { if flag { left } else { right } }
fn unit(value: i32) { value; }

fn shadowed(outer: i32, inner: i32) -> i32 {
    let value = outer;
    { let value = inner; value }
}
