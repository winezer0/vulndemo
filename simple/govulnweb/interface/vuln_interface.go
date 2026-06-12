package iface

// VulnExecutor 漏洞执行接口
// Go 语言隐式实现接口，实现结构体无需声明 implements
type VulnExecutor interface {
	// ExecuteCommand 执行系统命令
	ExecuteCommand(input string) string
	// ExecuteCode 执行代码（构造命令）
	ExecuteCode(template string, args ...interface{}) string
	// DoSSRF 发起SSRF请求
	DoSSRF(url string) string
}
