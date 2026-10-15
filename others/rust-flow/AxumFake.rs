use fake_axum::Router;
fn main() { Router::new().route("/fake", get(handler)); }
