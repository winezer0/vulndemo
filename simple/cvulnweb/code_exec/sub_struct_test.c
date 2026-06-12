/**
 * code_exec/sub_struct_test.c - 代码执行漏洞测试（纯结构体继承调用）
 * 测试子结构体调用父结构体代码执行方法
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../common/vuln_parent.h"
#include "../common/vuln_child.h"

/**
 * 代码执行漏洞测试 - 子结构体继承调用
 * 调用链：HTTP参数 -> 测试函数 -> 子结构体 -> 父结构体 -> 代码执行漏洞
 */
int code_exec_sub_struct_test(const char *param) {
    printf("[CODE_EXEC_SUB_STRUCT] 开始代码执行漏洞测试（子结构体继承调用）\n");
    
    // 创建子结构体实例
    VulnChild child;
    vuln_child_init(&child, "CodeExecParent", 7, "CodeExecChild", 8);
    
    // 调用子结构体的代码执行方法（内部调用父结构体方法）
    int result = vuln_child_code_exec(&child, param);
    
    // 销毁结构体
    vuln_child_destroy(&child);
    
    printf("[CODE_EXEC_SUB_STRUCT] 测试完成，结果: %d\n", result);
    return result;
}

/**
 * 代码执行漏洞测试 - 硬编码参数（无外部输入）
 */
int code_exec_sub_struct_hardcoded(void) {
    printf("[CODE_EXEC_SUB_STRUCT] 开始代码执行漏洞测试（硬编码参数）\n");
    
    // 创建子结构体实例
    VulnChild child;
    vuln_child_init(&child, "HardcodedCodeExecParent", 9, "HardcodedCodeExecChild", 10);
    
    // 使用硬编码参数调用代码执行方法
    int result = vuln_child_code_exec(&child, "print('hardcoded code execution')");
    
    // 销毁结构体
    vuln_child_destroy(&child);
    
    printf("[CODE_EXEC_SUB_STRUCT] 硬编码测试完成，结果: %d\n", result);
    return result;
}