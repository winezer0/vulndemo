package code_exec

import (
	"fmt"
	"net/http"
	"os/exec"

	iface "govulnweb/interface"

	"github.com/gin-gonic/gin"
)

// CodeInterfaceImpl 代码执行漏洞 - 接口实现结构体
// 隐式实现 VulnExecutor 接口
type CodeInterfaceImpl struct {
	Name string
}

// ExecuteCommand 隐式实现 VulnExecutor 接口
func (i *CodeInterfaceImpl) ExecuteCommand(input string) string {
	// 这个结构体主要做代码执行，命令执行由其他结构体实现
	return "CodeInterfaceImpl 不支持直接命令执行"
}

// ExecuteCode 隐式实现 VulnExecutor 接口的 ExecuteCode 方法
func (i *CodeInterfaceImpl) ExecuteCode(template string, args ...interface{}) string {
	// [漏洞点] 代码执行 - 通过 fmt.Sprintf 构造命令，类似 eval 效果
	cmdStr := fmt.Sprintf(template, args...)
	// [漏洞点] 将构造的命令字符串传递给 shell 执行
	cmd := exec.Command("sh", "-c", cmdStr)
	out, err := cmd.CombinedOutput()
	if err != nil {
		return fmt.Sprintf("接口实现代码执行失败: %v, 命令: %s", err, cmdStr)
	}
	return string(out)
}

// DoSSRF 隐式实现 VulnExecutor 接口
func (i *CodeInterfaceImpl) DoSSRF(url string) string {
	// 这个结构体主要做代码执行
	return "CodeInterfaceImpl 不支持 SSRF"
}

// InterfaceHandler 接口实现调用测试入口 handler
func InterfaceHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入
	cmdTemplate := c.Query("template")
	if cmdTemplate == "" {
		cmdTemplate = "echo code_interface_%s"
	}
	cmdArg := c.Query("arg")
	if cmdArg == "" {
		cmdArg = "default"
	}

	// 通过接口类型接收实现，验证隐式接口实现
	var executor iface.VulnExecutor = &CodeInterfaceImpl{Name: "code_impl"}

	// 调用链: 请求参数 -> handler -> 接口调用 -> 实现结构体方法 -> fmt.Sprintf 构造命令 -> 代码执行漏洞点
	result := executor.ExecuteCode(cmdTemplate, cmdArg)

	c.JSON(http.StatusOK, gin.H{
		"vuln_type": "code_exec",
		"call_type": "interface_impl",
		"template":  cmdTemplate,
		"arg":       cmdArg,
		"result":    result,
	})
}
