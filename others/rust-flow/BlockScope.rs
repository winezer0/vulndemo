fn sink(_: i32) {}

fn scoped_return(outer: i32, inner: i32) -> i32 {
    let value = outer;
    { let value = inner; sink(value); }
    sink(value);
    return value;
}

fn outer(value: i32) -> i32 {
    fn inner(value: i32) -> i32 { return value; }
    return value;
}

fn closure_owner(value: i32) -> i32 {
    let callback = || { return value + 1; };
    return value;
}

fn first(values: &mut [i32]) { let _ = values[0]; values[0] = 1; } fn second(values: &mut [i32]) { let _ = values[1]; values[1] = 2; }
