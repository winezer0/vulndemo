fn projected_pair(input: String) -> (String, String) {
    let value = rs_source();
    (String::new(), value)
}

fn projected_caller(value: String) {
    let (first, second) = projected_pair(value);
    sink(second);
}
