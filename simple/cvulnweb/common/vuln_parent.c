/**
 * vuln_parent.c - 父结构体方法实现
 * 实现所有漏洞核心方法
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "vuln_parent.h"
#include "../func_call.h"

/**
 * 初始化父结构体
 */
void vuln_parent_init(VulnParent *parent, const char *name, int id) {
    if (parent == NULL) {
        return;
    }
    
    // 设置函数指针
    parent->cmd_exec = vuln_parent_cmd_exec;
    parent->code_exec = vuln_parent_code_exec;
    parent->ssrf = vuln_parent_ssrf;
    
    // 设置数据成员
    strncpy(parent->name, name, sizeof(parent->name) - 1);
    parent->name[sizeof(parent->name) - 1] = '\0';
    parent->id = id;
    
    printf("[VULN_PARENT] 初始化父结构体: %s (ID: %d)\n", parent->name, parent->id);
}

/**
 * 父结构体命令执行方法
 * 核心漏洞点：调用 system() 执行命令
 */
int vuln_parent_cmd_exec(VulnParent *self, const char *param) {
    if (self == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_PARENT] 命令执行方法被调用, 参数: %s\n", param);
    
    // 构造命令字符串
    char cmd[1024];
    snprintf(cmd, sizeof(cmd), "echo 'Executing command from parent: %s'", param);
    
    // 漏洞点：直接使用 system() 执行构造的命令
    return execute_command(cmd);
}

/**
 * 父结构体代码执行方法
 * 核心漏洞点：执行任意代码
 */
int vuln_parent_code_exec(VulnParent *self, const char *param) {
    if (self == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_PARENT] 代码执行方法被调用, 参数: %s\n", param);
    
    // 漏洞点：将参数作为代码执行
    return execute_code(param);
}

/**
 * 父结构体SSRF方法
 * 核心漏洞点：发起网络请求到用户指定的URL
 */
int vuln_parent_ssrf(VulnParent *self, const char *param) {
    if (self == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_PARENT] SSRF方法被调用, 参数: %s\n", param);
    
    // 漏洞点：直接请求用户指定的URL
    return perform_request(param);
}

/**
 * 父结构体析构
 */
void vuln_parent_destroy(VulnParent *parent) {
    if (parent == NULL) {
        return;
    }
    
    printf("[VULN_PARENT] 销毁父结构体: %s\n", parent->name);
    // 清理资源（此处无需特殊清理）
}