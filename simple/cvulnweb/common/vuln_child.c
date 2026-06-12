/**
 * vuln_child.c - 子结构体方法实现
 * 实现子结构体调用父结构体漏洞方法
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "vuln_child.h"

/**
 * 初始化子结构体
 */
void vuln_child_init(VulnChild *child, const char *parent_name, int parent_id,
                     const char *child_name, int child_id) {
    if (child == NULL) {
        return;
    }
    
    // 初始化父结构体部分
    vuln_parent_init(&child->parent, parent_name, parent_id);
    
    // 设置子结构体特有数据
    strncpy(child->child_name, child_name, sizeof(child->child_name) - 1);
    child->child_name[sizeof(child->child_name) - 1] = '\0';
    child->child_id = child_id;
    
    printf("[VULN_CHILD] 初始化子结构体: %s (ID: %d)\n", child->child_name, child->child_id);
}

/**
 * 子结构体调用父结构体命令执行方法
 * 调用链：子结构体 -> 父结构体 -> 漏洞点
 */
int vuln_child_cmd_exec(VulnChild *child, const char *param) {
    if (child == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_CHILD] 调用父结构体命令执行方法, 参数: %s\n", param);
    
    // 调用父结构体的命令执行方法（通过函数指针）
    return child->parent.cmd_exec(&child->parent, param);
}

/**
 * 子结构体调用父结构体代码执行方法
 * 调用链：子结构体 -> 父结构体 -> 漏洞点
 */
int vuln_child_code_exec(VulnChild *child, const char *param) {
    if (child == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_CHILD] 调用父结构体代码执行方法, 参数: %s\n", param);
    
    // 调用父结构体的代码执行方法（通过函数指针）
    return child->parent.code_exec(&child->parent, param);
}

/**
 * 子结构体调用父结构体SSRF方法
 * 调用链：子结构体 -> 父结构体 -> 漏洞点
 */
int vuln_child_ssrf(VulnChild *child, const char *param) {
    if (child == NULL || param == NULL) {
        return -1;
    }
    
    printf("[VULN_CHILD] 调用父结构体SSRF方法, 参数: %s\n", param);
    
    // 调用父结构体的SSRF方法（通过函数指针）
    return child->parent.ssrf(&child->parent, param);
}

/**
 * 子结构体析构
 */
void vuln_child_destroy(VulnChild *child) {
    if (child == NULL) {
        return;
    }
    
    printf("[VULN_CHILD] 销毁子结构体: %s\n", child->child_name);
    
    // 先销毁父结构体部分
    vuln_parent_destroy(&child->parent);
}