use actix_web::web;

fn first() { web::resource("/first-builder").route(web::get().to(handler)); } fn second() { web::resource("/second-builder").route(web::post().to(handler)); }

fn handler() {}
