use axum::Router;
fn handler() {}
fn main() {
    Router::new().route(r#"/raw/items"#, axum::routing::get(handler));
    Router::new().route(dynamic_path(), axum::routing::post(handler));
}
