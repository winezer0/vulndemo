package common

// VulnChild 子结构体，嵌入 VulnParent 实现结构体组合
// 通过嵌入父结构体，子结构体自动获得父结构体的所有漏洞方法
type VulnChild struct {
	VulnParent // 嵌入父结构体，实现结构体组合
	ChildName  string
}

// ChildCommandExecute 子结构体自己的命令执行方法，内部调用父结构体漏洞方法
func (c *VulnChild) ChildCommandExecute(input string) string {
	// 调用链: 子结构体方法 -> 父结构体 CommandExecute -> 命令执行漏洞点
	return c.CommandExecute(input)
}

// ChildCodeExecute 子结构体自己的代码执行方法，内部调用父结构体漏洞方法
func (c *VulnChild) ChildCodeExecute(template string, args ...interface{}) string {
	// 调用链: 子结构体方法 -> 父结构体 CodeExecute -> 代码执行漏洞点
	return c.CodeExecute(template, args...)
}

// ChildSSRFRequest 子结构体自己的SSRF方法，内部调用父结构体漏洞方法
func (c *VulnChild) ChildSSRFRequest(url string) string {
	// 调用链: 子结构体方法 -> 父结构体 SSRFRequest -> SSRF漏洞点
	return c.SSRFRequest(url)
}

// ChildHardcodeCommand 子结构体硬编码命令执行，调用父结构体方法
func (c *VulnChild) ChildHardcodeCommand() string {
	// 调用链: 子结构体 -> 父结构体 HardcodeCommand -> 硬编码命令执行
	return c.HardcodeCommand()
}

// ChildHardcodeSSRF 子结构体硬编码SSRF，调用父结构体方法
func (c *VulnChild) ChildHardcodeSSRF() string {
	// 调用链: 子结构体 -> 父结构体 HardcodeSSRF -> 硬编码SSRF
	return c.HardcodeSSRF()
}
