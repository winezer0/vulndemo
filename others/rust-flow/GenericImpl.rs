struct Boxed<T> {
    value: T,
}

impl<T> Boxed<T> {
    fn get(&self) -> &T {
        &self.value
    }
}

fn run(boxed: Boxed<i32>) {
    let _ = boxed.get();
}
