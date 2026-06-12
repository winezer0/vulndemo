package cmd_exec

import (
	"govulnweb/common"
	"net/http"

	"github.com/gin-gonic/gin"
)

// CmdSubStruct 命令执行漏洞 - 结构体调用测试结构体
// 嵌入 VulnChild，通过多层调用链触发命令执行漏洞
type CmdSubStruct struct {
	common.VulnChild // 嵌入子结构体（子结构体又嵌入父结构体）
	Module           string
}

// Process 命令执行处理方法，形成完整调用链
func (s *CmdSubStruct) Process(input string) string {
	// 调用链: Process -> ChildCommandExecute -> CommandExecute -> 命令注入
	return s.ChildCommandExecute(input)
}

// ProcessHardcode 硬编码命令执行，无外部数据源
func (s *CmdSubStruct) ProcessHardcode() string {
	// 调用链: ProcessHardcode -> ChildHardcodeCommand -> HardcodeCommand -> 硬编码命令执行
	return s.ChildHardcodeCommand()
}

// SubStructHandler 结构体调用测试入口 handler
func SubStructHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入
	input := c.Query("cmd")
	if input == "" {
		input = "echo default_cmd"
	}

	// 创建结构体实例，形成多层嵌入
	s := &CmdSubStruct{
		VulnChild: common.VulnChild{
			VulnParent: common.VulnParent{Name: "cmd_exec"},
			ChildName:  "cmd_child",
		},
		Module: "cmd_exec",
	}

	// 场景1: 外部可控数据源 - 命令执行
	result1 := s.Process(input)
	// 场景2: 硬编码无外部数据源 - 命令执行
	result2 := s.ProcessHardcode()

	c.JSON(http.StatusOK, gin.H{
		"vuln_type":       "cmd_exec",
		"call_type":       "sub_struct",
		"input":           input,
		"result":          result1,
		"hardcode_result": result2,
	})
}
