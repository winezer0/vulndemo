package ssrf

import (
	"fmt"
	"net/http"

	iface "govulnweb/interface"

	"github.com/gin-gonic/gin"
)

// SSRFInterfaceImpl SSRF漏洞 - 接口实现结构体
// 隐式实现 VulnExecutor 接口
type SSRFInterfaceImpl struct {
	Name string
}

// ExecuteCommand 隐式实现 VulnExecutor 接口
func (i *SSRFInterfaceImpl) ExecuteCommand(input string) string {
	// 这个结构体主要做 SSRF
	return "SSRFInterfaceImpl 不支持命令执行"
}

// ExecuteCode 隐式实现 VulnExecutor 接口
func (i *SSRFInterfaceImpl) ExecuteCode(template string, args ...interface{}) string {
	// 这个结构体主要做 SSRF
	return "SSRFInterfaceImpl 不支持代码执行"
}

// DoSSRF 隐式实现 VulnExecutor 接口的 DoSSRF 方法
func (i *SSRFInterfaceImpl) DoSSRF(url string) string {
	// [漏洞点] SSRF - 使用外部可控 URL 发起 HTTP 请求
	resp, err := http.Get(url)
	if err != nil {
		return fmt.Sprintf("接口实现SSRF失败: %v", err)
	}
	defer resp.Body.Close()
	return fmt.Sprintf("接口实现SSRF状态码: %d, 目标: %s", resp.StatusCode, url)
}

// InterfaceHandler 接口实现调用测试入口 handler
func InterfaceHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入
	targetURL := c.Query("url")
	if targetURL == "" {
		targetURL = "http://127.0.0.1:9999/default_interface"
	}

	// 通过接口类型接收实现，验证隐式接口实现
	var executor iface.VulnExecutor = &SSRFInterfaceImpl{Name: "ssrf_impl"}

	// 调用链: 请求参数 -> handler -> 接口调用 -> 实现结构体方法 -> http.Get -> SSRF漏洞点
	result := executor.DoSSRF(targetURL)

	c.JSON(http.StatusOK, gin.H{
		"vuln_type": "ssrf",
		"call_type": "interface_impl",
		"url":       targetURL,
		"result":    result,
	})
}
