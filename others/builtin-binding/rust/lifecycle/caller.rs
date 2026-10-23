use crate::types::{Alpha, Beta};
pub fn run(input: i32) -> i32 {
    let client: Alpha = Alpha;
    let first = client.hit(input);
    {
        let client: Beta = Beta;
        let _second = client.hit(input);
    }
    client.hit(first)
}
