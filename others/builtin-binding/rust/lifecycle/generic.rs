trait Action {
    fn hit(&self, input: i32) -> i32;
}
pub fn run<T: Action>(client: T) -> i32 {
    client.hit(1)
}
