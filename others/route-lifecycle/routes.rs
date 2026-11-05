use axum::Router;

fn auth() {}
fn handler() {}

fn setup() {
    let app = Router::new()
        .route("/rs", axum::routing::get(handler))
        .route_layer(auth)
        .route("/rs-open", axum::routing::get(handler));
}
