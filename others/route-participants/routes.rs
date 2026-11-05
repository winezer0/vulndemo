use axum::{routing::get, Router};

async fn serve() -> &'static str { "ok" }
fn app() -> Router {
    Router::new().route("/rust", get(serve))
}
