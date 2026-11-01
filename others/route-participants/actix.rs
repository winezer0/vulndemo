use actix_web::get;
#[get("/actix")]
async fn serve() -> &'static str { "ok" }
