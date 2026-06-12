use actix_web::{web, App, HttpServer, HttpRequest, HttpResponse, Responder};
use std::sync::Mutex;

// 导入其他模块
mod func_call;
mod common;
mod trait_def;
mod cmd_exec;
mod code_exec;
mod ssrf;

// 共享状态（可选，用于演示）
struct AppState {
    counter: Mutex<i32>,
}

// 首页处理器，返回测试入口页面
async fn index() -> impl Responder {
    HttpResponse::Ok()
        .content_type("text/html; charset=utf-8")
        .body(include_str!("../static/index.html"))
}

// 跨文件函数调用测试
async fn test_func_call(req: HttpRequest) -> impl Responder {
    let param = req.query_string().to_string();
    let result = func_call::vuln_function(&param);
    HttpResponse::Ok().body(format!("跨文件函数调用结果: {}", result))
}

// 命令执行漏洞测试 - 结构体组合调用
async fn test_cmd_exec_struct(req: HttpRequest) -> impl Responder {
    let cmd = req.query_string().to_string();
    let result = cmd_exec::sub_struct_test::test_cmd_exec_struct(&cmd);
    HttpResponse::Ok().body(format!("命令执行(结构体组合)结果: {}", result))
}

// 命令执行漏洞测试 - trait实现调用
async fn test_cmd_exec_trait(req: HttpRequest) -> impl Responder {
    let cmd = req.query_string().to_string();
    let result = cmd_exec::trait_impl::test_cmd_exec_trait(&cmd);
    HttpResponse::Ok().body(format!("命令执行(trait实现)结果: {}", result))
}

// 代码执行漏洞测试 - 结构体组合调用
async fn test_code_exec_struct(req: HttpRequest) -> impl Responder {
    let code = req.query_string().to_string();
    let result = code_exec::sub_struct_test::test_code_exec_struct(&code);
    HttpResponse::Ok().body(format!("代码执行(结构体组合)结果: {}", result))
}

// 代码执行漏洞测试 - trait实现调用
async fn test_code_exec_trait(req: HttpRequest) -> impl Responder {
    let code = req.query_string().to_string();
    let result = code_exec::trait_impl::test_code_exec_trait(&code);
    HttpResponse::Ok().body(format!("代码执行(trait实现)结果: {}", result))
}

// SSRF漏洞测试 - 结构体组合调用
async fn test_ssrf_struct(req: HttpRequest) -> impl Responder {
    let url = req.query_string().to_string();
    let result = ssrf::sub_struct_test::test_ssrf_struct(&url).await;
    HttpResponse::Ok().body(format!("SSRF(结构体组合)结果: {}", result))
}

// SSRF漏洞测试 - trait实现调用
async fn test_ssrf_trait(req: HttpRequest) -> impl Responder {
    let url = req.query_string().to_string();
    let result = ssrf::trait_impl::test_ssrf_trait(&url);
    HttpResponse::Ok().body(format!("SSRF(trait实现)结果: {}", result))
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let data = web::Data::new(AppState {
        counter: Mutex::new(0),
    });

    println!("启动服务器: http://127.0.0.1:8080");

    HttpServer::new(move || {
        App::new()
            .app_data(data.clone())
            .route("/", web::get().to(index))
            .route("/func_call", web::get().to(test_func_call))
            .route("/cmd_exec_struct", web::get().to(test_cmd_exec_struct))
            .route("/cmd_exec_trait", web::get().to(test_cmd_exec_trait))
            .route("/code_exec_struct", web::get().to(test_code_exec_struct))
            .route("/code_exec_trait", web::get().to(test_code_exec_trait))
            .route("/ssrf_struct", web::get().to(test_ssrf_struct))
            .route("/ssrf_trait", web::get().to(test_ssrf_trait))
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}
