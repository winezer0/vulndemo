package code_exec

import (
	"govulnweb/common"
	"net/http"

	"github.com/gin-gonic/gin"
)

// CodeSubStruct 代码执行漏洞 - 结构体调用测试结构体
// 嵌入 VulnChild，通过多层调用链触发代码执行漏洞
type CodeSubStruct struct {
	common.VulnChild // 嵌入子结构体
	Module           string
}

// Process 代码执行处理方法，形成完整调用链
func (s *CodeSubStruct) Process(template string, args ...interface{}) string {
	// 调用链: Process -> ChildCodeExecute -> CodeExecute -> 代码执行漏洞点
	return s.ChildCodeExecute(template, args...)
}

// ProcessHardcode 硬编码代码执行，无外部数据源
func (s *CodeSubStruct) ProcessHardcode() string {
	// 硬编码代码执行模板
	template := "echo code_exec_hardcode_%s"
	// 调用链: ProcessHardcode -> ChildCodeExecute -> CodeExecute -> 硬编码代码执行
	return s.ChildCodeExecute(template, "test")
}

// SubStructHandler 结构体调用测试入口 handler
func SubStructHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入（命令模板）
	cmdTemplate := c.Query("template")
	if cmdTemplate == "" {
		cmdTemplate = "echo code_exec_%s"
	}
	cmdArg := c.Query("arg")
	if cmdArg == "" {
		cmdArg = "default"
	}

	// 创建结构体实例
	s := &CodeSubStruct{
		VulnChild: common.VulnChild{
			VulnParent: common.VulnParent{Name: "code_exec"},
			ChildName:  "code_child",
		},
		Module: "code_exec",
	}

	// 场景1: 外部可控数据源 - 代码执行（通过 fmt.Sprintf 构造命令）
	result1 := s.Process(cmdTemplate, cmdArg)
	// 场景2: 硬编码无外部数据源 - 代码执行
	result2 := s.ProcessHardcode()

	c.JSON(http.StatusOK, gin.H{
		"vuln_type":       "code_exec",
		"call_type":       "sub_struct",
		"template":        cmdTemplate,
		"arg":             cmdArg,
		"result":          result1,
		"hardcode_result": result2,
	})
}
