package ssrf

import (
	"govulnweb/common"
	"net/http"

	"github.com/gin-gonic/gin"
)

// SSRFSubStruct SSRF漏洞 - 结构体调用测试结构体
// 嵌入 VulnChild，通过多层调用链触发 SSRF 漏洞
type SSRFSubStruct struct {
	common.VulnChild // 嵌入子结构体
	Module           string
}

// Process SSRF处理方法，形成完整调用链
func (s *SSRFSubStruct) Process(url string) string {
	// 调用链: Process -> ChildSSRFRequest -> SSRFRequest -> SSRF漏洞点
	return s.ChildSSRFRequest(url)
}

// ProcessHardcode 硬编码SSRF，无外部数据源
func (s *SSRFSubStruct) ProcessHardcode() string {
	// 调用链: ProcessHardcode -> ChildHardcodeSSRF -> HardcodeSSRF -> 硬编码SSRF
	return s.ChildHardcodeSSRF()
}

// SubStructHandler 结构体调用测试入口 handler
func SubStructHandler(c *gin.Context) {
	// 从请求参数获取外部可控输入（目标URL）
	targetURL := c.Query("url")
	if targetURL == "" {
		targetURL = "http://127.0.0.1:9999/default"
	}

	// 创建结构体实例
	s := &SSRFSubStruct{
		VulnChild: common.VulnChild{
			VulnParent: common.VulnParent{Name: "ssrf"},
			ChildName:  "ssrf_child",
		},
		Module: "ssrf",
	}

	// 场景1: 外部可控数据源 - SSRF
	result1 := s.Process(targetURL)
	// 场景2: 硬编码无外部数据源 - SSRF
	result2 := s.ProcessHardcode()

	c.JSON(http.StatusOK, gin.H{
		"vuln_type":       "ssrf",
		"call_type":       "sub_struct",
		"url":             targetURL,
		"result":          result1,
		"hardcode_result": result2,
	})
}
