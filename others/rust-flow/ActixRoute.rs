use actix_web::{get, post};
#[get("/users")]
async fn users() -> &'static str { "ok" }
#[get(PATH)]
async fn dynamic() -> &'static str { "ok" }
#[get("/read")]
#[post("/write")]
async fn shared() -> &'static str { "ok" }
