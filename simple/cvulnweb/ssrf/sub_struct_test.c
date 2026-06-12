/**
 * ssrf/sub_struct_test.c - SSRF漏洞测试（纯结构体继承调用）
 * 测试子结构体调用父结构体SSRF方法
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../common/vuln_parent.h"
#include "../common/vuln_child.h"

/**
 * SSRF漏洞测试 - 子结构体继承调用
 * 调用链：HTTP参数 -> 测试函数 -> 子结构体 -> 父结构体 -> SSRF漏洞
 */
int ssrf_sub_struct_test(const char *param) {
    printf("[SSRF_SUB_STRUCT] 开始SSRF漏洞测试（子结构体继承调用）\n");
    
    // 创建子结构体实例
    VulnChild child;
    vuln_child_init(&child, "SSRFParent", 13, "SSRFChild", 14);
    
    // 调用子结构体的SSRF方法（内部调用父结构体方法）
    int result = vuln_child_ssrf(&child, param);
    
    // 销毁结构体
    vuln_child_destroy(&child);
    
    printf("[SSRF_SUB_STRUCT] 测试完成，结果: %d\n", result);
    return result;
}

/**
 * SSRF漏洞测试 - 硬编码参数（无外部输入）
 */
int ssrf_sub_struct_hardcoded(void) {
    printf("[SSRF_SUB_STRUCT] 开始SSRF漏洞测试（硬编码参数）\n");
    
    // 创建子结构体实例
    VulnChild child;
    vuln_child_init(&child, "HardcodedSSRFParent", 15, "HardcodedSSRFChild", 16);
    
    // 使用硬编码参数调用SSRF方法
    int result = vuln_child_ssrf(&child, "http://127.0.0.1:8080");
    
    // 销毁结构体
    vuln_child_destroy(&child);
    
    printf("[SSRF_SUB_STRUCT] 硬编码测试完成，结果: %d\n", result);
    return result;
}