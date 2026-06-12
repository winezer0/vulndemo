#include "crow.h"
#include "func_call.h"
#include "common/vuln_child.h"
#include "interface/vuln_interface.h"

#include <iostream>
#include <string>

// 主函数，Crow服务器入口
int main() {
    crow::SimpleApp app;

    // 主页面路由，提供测试入口
    CROW_ROUTE(app, "/")
    ([](){
        crow::response res;
        res.set_static_file_root("static");
        res.set_file("index.html");
        return res;
    });

    // 命令执行漏洞测试路由 - 子类继承调用
    CROW_ROUTE(app, "/cmd_exec/sub_class")
    ([](const crow::request& req){
        // 从请求参数获取命令
        auto param = req.url_params.get("cmd");
        std::string cmd = param ? param : "echo default";
        
        // 创建子类实例，调用父类漏洞方法
        VulnChild child;
        std::string result = child.executeCommand(cmd);
        
        return crow::response(result);
    });

    // 命令执行漏洞测试路由 - 接口实现调用
    CROW_ROUTE(app, "/cmd_exec/interface_impl")
    ([](const crow::request& req){
        auto param = req.url_params.get("cmd");
        std::string cmd = param ? param : "echo default";
        
        // 创建接口实现类实例，调用接口方法
        CmdExecInterfaceImpl impl;
        std::string result = impl.executeCommandViaInterface(cmd);
        
        return crow::response(result);
    });

    // 代码执行漏洞测试路由 - 子类继承调用
    CROW_ROUTE(app, "/code_exec/sub_class")
    ([](const crow::request& req){
        auto param = req.url_params.get("code");
        std::string code = param ? param : "1+1";
        
        VulnChild child;
        std::string result = child.executeCode(code);
        
        return crow::response(result);
    });

    // 代码执行漏洞测试路由 - 接口实现调用
    CROW_ROUTE(app, "/code_exec/interface_impl")
    ([](const crow::request& req){
        auto param = req.url_params.get("code");
        std::string code = param ? param : "1+1";
        
        CodeExecInterfaceImpl impl;
        std::string result = impl.executeCodeViaInterface(code);
        
        return crow::response(result);
    });

    // SSRF漏洞测试路由 - 子类继承调用
    CROW_ROUTE(app, "/ssrf/sub_class")
    ([](const crow::request& req){
        auto param = req.url_params.get("url");
        std::string url = param ? param : "http://example.com";
        
        VulnChild child;
        std::string result = child.fetchUrl(url);
        
        return crow::response(result);
    });

    // SSRF漏洞测试路由 - 接口实现调用
    CROW_ROUTE(app, "/ssrf/interface_impl")
    ([](const crow::request& req){
        auto param = req.url_params.get("url");
        std::string url = param ? param : "http://example.com";
        
        SsrfInterfaceImpl impl;
        std::string result = impl.fetchUrlViaInterface(url);
        
        return crow::response(result);
    });

    // 跨文件函数调用测试路由
    CROW_ROUTE(app, "/func_call")
    ([](const crow::request& req){
        auto param = req.url_params.get("input");
        std::string input = param ? param : "default";
        
        std::string result = funcCallTest(input);
        
        return crow::response(result);
    });

    // 硬编码漏洞测试路由
    CROW_ROUTE(app, "/hardcoded")
    ([](){
        std::string result = hardcodedVulnTest();
        return crow::response(result);
    });

    // 启动服务器，监听端口18080
    app.port(18080).multithreaded().run();

    return 0;
}