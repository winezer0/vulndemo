class UrlMappings {

    static mappings = {
        // 默认首页
        "/"(controller: "funcCall", action: "index")
        
        // 命令执行测试
        "/cmdExec/subClassTest"(controller: "cmdExec", action: "subClassTest")
        "/cmdExec/traitImplTest"(controller: "cmdExec", action: "traitImplTest")
        "/cmdExec/directTest"(controller: "cmdExec", action: "directTest")
        
        // 代码执行测试
        "/codeExec/subClassTest"(controller: "codeExec", action: "subClassTest")
        "/codeExec/traitImplTest"(controller: "codeExec", action: "traitImplTest")
        "/codeExec/directTest"(controller: "codeExec", action: "directTest")
        
        // SSRF测试
        "/ssrf/subClassTest"(controller: "ssrf", action: "subClassTest")
        "/ssrf/traitImplTest"(controller: "ssrf", action: "traitImplTest")
        "/ssrf/directTest"(controller: "ssrf", action: "directTest")
        
        // 跨文件调用测试
        "/funcCall/test"(controller: "funcCall", action: "test")
        
        // 404
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
