package main

import (
	"govulnweb/common"
	"net/http"
	"os/exec"

	"github.com/gin-gonic/gin"
)

// crossFileCall 跨文件函数调用示例，调用 common 包中的漏洞方法
func crossFileCall(input string) string {
	parent := &common.VulnParent{}
	// 调用父结构体的命令执行方法
	return parent.CommandExecute(input)
}

// hardcodeCmdExec 硬编码命令执行漏洞测试，无外部数据源
func hardcodeCmdExec() string {
	// [漏洞点] 硬编码命令执行 - 无外部数据源场景
	cmd := exec.Command("sh", "-c", "echo hardcoded_vuln_test")
	out, err := cmd.CombinedOutput()
	if err != nil {
		return "执行失败: " + err.Error()
	}
	return string(out)
}

// hardcodeCodeExec 硬编码代码执行漏洞测试，无外部数据源
func hardcodeCodeExec() string {
	// [漏洞点] 硬编码代码执行 - fmt.Sprintf 构造命令
	cmdStr := "echo hardcoded_code_exec_test"
	out, err := exec.Command("sh", "-c", cmdStr).CombinedOutput()
	if err != nil {
		return "执行失败: " + err.Error()
	}
	return string(out)
}

// hardcodeSSRF 硬编码SSRF漏洞测试，无外部数据源
func hardcodeSSRF() string {
	// [漏洞点] 硬编码SSRF - 固定URL
	resp, err := http.Get("http://127.0.0.1:9999/internal")
	if err != nil {
		return "请求失败: " + err.Error()
	}
	defer resp.Body.Close()
	return resp.Status
}

// funcCallHandler 跨文件函数调用入口，演示多种调用方式
func funcCallHandler(c *gin.Context) {
	// 从请求参数获取输入
	input := c.Query("cmd")
	if input == "" {
		input = "default_input"
	}

	// 场景1: 跨文件函数调用（调用 common 包父结构体方法）
	result1 := crossFileCall(input)
	// 场景2: 硬编码命令执行
	result2 := hardcodeCmdExec()
	// 场景3: 硬编码代码执行
	result3 := hardcodeCodeExec()
	// 场景4: 硬编码SSRF
	result4 := hardcodeSSRF()

	c.JSON(http.StatusOK, gin.H{
		"cross_file_call": result1,
		"hardcode_cmd":    result2,
		"hardcode_code":   result3,
		"hardcode_ssrf":   result4,
	})
}
