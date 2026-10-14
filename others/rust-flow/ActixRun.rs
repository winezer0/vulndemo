use actix_web::HttpServer;
fn run() {}
fn other() { run(); }
fn main() { HttpServer::new(|| {}).run(); }
