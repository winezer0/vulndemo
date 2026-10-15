use axum::Router;
fn handler() {}
fn main() { let app = Router::new().route("/items", axum::routing::get(handler)); }
