pub struct Alpha;
pub struct Beta;
impl Alpha {
    pub fn hit(&self, input: i32) -> i32 { input }
}
impl Beta {
    pub fn hit(&self, input: i32) -> i32 { input + 1 }
}
