use axum::Router;
fn first() { Router::new().route("/first", axum::routing::get(first)); } fn second() { Router::new().route("/second", axum::routing::post(second)); }
