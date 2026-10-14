fn run(body: i32) {
    object.json(body);
    json(body);
    HttpResponse::Ok().json(body);
    actix_web::HttpResponse::Ok().json(body);
}
