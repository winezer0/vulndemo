package main

import (
	"govulnweb/cmd_exec"
	"govulnweb/code_exec"
	"govulnweb/ssrf"
	"html/template"
	"net/http"

	"github.com/gin-gonic/gin"
)

// indexHandler 处理首页请求，汇总所有测试用例链接
func indexHandler(c *gin.Context) {
	tmpl, err := template.ParseFiles("static/index.html")
	if err != nil {
		c.String(http.StatusInternalServerError, "模板解析失败: %v", err)
		return
	}
	tmpl.Execute(c.Writer, nil)
}

func main() {
	r := gin.Default()

	// 首页入口
	r.GET("/", indexHandler)
	r.GET("/index.html", indexHandler)

	// 命令执行漏洞 - 结构体调用
	r.GET("/cmd/sub_struct", cmd_exec.SubStructHandler)
	// 命令执行漏洞 - 接口实现调用
	r.GET("/cmd/interface", cmd_exec.InterfaceHandler)

	// 代码执行漏洞 - 结构体调用
	r.GET("/code/sub_struct", code_exec.SubStructHandler)
	// 代码执行漏洞 - 接口实现调用
	r.GET("/code/interface", code_exec.InterfaceHandler)

	// SSRF漏洞 - 结构体调用
	r.GET("/ssrf/sub_struct", ssrf.SubStructHandler)
	// SSRF漏洞 - 接口实现调用
	r.GET("/ssrf/interface", ssrf.InterfaceHandler)

	// 跨文件函数调用
	r.GET("/func_call", funcCallHandler)

	r.Run(":8080")
}
