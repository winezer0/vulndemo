package common

import (
	"fmt"
	"net/http"
	"os/exec"
)

// VulnParent 父结构体，集中存放所有漏洞核心方法
// 所有漏洞方法都在此结构体中定义，子结构体通过嵌入复用
type VulnParent struct {
	Name string
}

// CommandExecute [漏洞点] 命令执行 - 通过 os/exec 直接拼接用户输入执行系统命令
func (v *VulnParent) CommandExecute(input string) string {
	// [漏洞点] 直接将 input 拼接到 shell 命令中，存在命令注入
	cmd := exec.Command("sh", "-c", input)
	out, err := cmd.CombinedOutput()
	if err != nil {
		return fmt.Sprintf("命令执行失败: %v, 输出: %s", err, string(out))
	}
	return string(out)
}

// CodeExecute [漏洞点] 代码执行 - 通过 fmt.Sprintf 构造命令字符串实现类似 eval 的效果
func (v *VulnParent) CodeExecute(template string, args ...interface{}) string {
	// [漏洞点] 使用 fmt.Sprintf 构造命令，如果 template 来自用户输入则可注入
	cmdStr := fmt.Sprintf(template, args...)
	cmd := exec.Command("sh", "-c", cmdStr)
	out, err := cmd.CombinedOutput()
	if err != nil {
		return fmt.Sprintf("代码执行失败: %v, 输出: %s", err, string(out))
	}
	return string(out)
}

// SSRFRequest [漏洞点] SSRF - 发起 HTTP 请求，URL 来自外部参数
func (v *VulnParent) SSRFRequest(url string) string {
	// [漏洞点] 直接使用外部传入的 URL 发起请求，存在 SSRF
	resp, err := http.Get(url)
	if err != nil {
		return fmt.Sprintf("SSRF 请求失败: %v", err)
	}
	defer resp.Body.Close()
	return fmt.Sprintf("状态码: %d, URL: %s", resp.StatusCode, url)
}

// HardcodeCommand 硬编码命令执行漏洞，无外部数据源
func (v *VulnParent) HardcodeCommand() string {
	// [漏洞点] 硬编码命令执行
	cmd := exec.Command("sh", "-c", "echo parent_hardcode_vuln")
	out, _ := cmd.CombinedOutput()
	return string(out)
}

// HardcodeSSRF 硬编码SSRF漏洞，无外部数据源
func (v *VulnParent) HardcodeSSRF() string {
	// [漏洞点] 硬编码SSRF请求
	resp, err := http.Get("http://127.0.0.1:9999/parent_internal")
	if err != nil {
		return fmt.Sprintf("硬编码SSRF失败: %v", err)
	}
	defer resp.Body.Close()
	return fmt.Sprintf("硬编码SSRF状态: %d", resp.StatusCode)
}
