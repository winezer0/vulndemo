package cmd_exec

import (
	"fmt"
	"net/http"
	"os/exec"

	iface "govulnweb/interface"

	"github.com/gin-gonic/gin"
)

// CmdInterfaceImpl 命令执行漏洞 - 接口实现结构体
// 隐式实现 VulnExecutor 接口，无需声明 implements
type CmdInterfaceImpl struct {
	Name string
}

// ExecuteCommand 隐式实现 VulnExecutor 接口的 ExecuteCommand 方法
func (i *CmdInterfaceImpl) ExecuteCommand(input string) string {
	// [漏洞点] 命令执行 - os/exec 直接拼接用户输入
	cmd := exec.Command("sh", "-c", input)
	out, err := cmd.CombinedOutput()
	if err != nil {
		return fmt.Sprintf("接口实现命令执行失败: %v", err)
	}
	return string(out)
}

// ExecuteCode 隐式实现 VulnExecutor 接口的 ExecuteCode 方法
func (i *CmdInterfaceImpl) ExecuteCode(template string, args ...interface{}) string {
	// [漏洞点] 通过 fmt.Sprintf 构造命令实现代码执行
	cmdStr := fmt.Sprintf(template, args...)
	cmd := exec.Command("sh", "-c", cmdStr)
	out, err := cmd.CombinedOutput()
	if err != nil {
		return fmt.Sprintf("接口实现代码执行失败: %v", err)
	}
	return string(out)
}

// DoSSRF 隐式实现 VulnExecutor 接口的 DoSSRF 方法
func (i *CmdInterfaceImpl) DoSSRF(url string) string {
	// 这个结构体主要做命令执行，SSRF 由其他结构体实现
	return "CmdInterfaceImpl 不支持 SSRF"
}

// InterfaceHandler 接口实现调用测试入口 handler
func InterfaceHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入
	input := c.Query("cmd")
	if input == "" {
		input = "echo default_interface_cmd"
	}

	// 通过接口类型接收实现，验证隐式接口实现
	var executor iface.VulnExecutor = &CmdInterfaceImpl{Name: "cmd_impl"}

	// 调用链: 请求参数 -> handler -> 接口调用 -> 实现结构体方法 -> 命令执行漏洞点
	result := executor.ExecuteCommand(input)

	c.JSON(http.StatusOK, gin.H{
		"vuln_type": "cmd_exec",
		"call_type": "interface_impl",
		"input":     input,
		"result":    result,
	})
}
