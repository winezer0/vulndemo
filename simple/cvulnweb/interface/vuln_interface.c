/**
 * vuln_interface.c - 接口通用工具函数实现
 * 实现接口的通用功能
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "vuln_interface.h"

/**
 * 初始化接口结构体
 */
void vuln_interface_init(VulnInterface *iface,
                         int (*cmd_exec_func)(void*, const char*),
                         int (*code_exec_func)(void*, const char*),
                         int (*ssrf_func)(void*, const char*),
                         void *impl_data) {
    if (iface == NULL) {
        return;
    }
    
    // 设置函数指针
    iface->cmd_exec = cmd_exec_func;
    iface->code_exec = code_exec_func;
    iface->ssrf = ssrf_func;
    
    // 设置实现数据
    iface->impl_data = impl_data;
    
    printf("[VULN_INTERFACE] 初始化接口结构体\n");
}

/**
 * 接口通用工具函数：执行命令
 * 漏洞点：通过接口函数指针调用实现
 */
int vuln_interface_cmd_exec(VulnInterface *iface, const char *param) {
    if (iface == NULL || param == NULL || iface->cmd_exec == NULL) {
        return -1;
    }
    
    printf("[VULN_INTERFACE] 通过接口调用命令执行, 参数: %s\n", param);
    
    // 通过接口函数指针调用实现
    return iface->cmd_exec(iface->impl_data, param);
}

/**
 * 接口通用工具函数：执行代码
 * 漏洞点：通过接口函数指针调用实现
 */
int vuln_interface_code_exec(VulnInterface *iface, const char *param) {
    if (iface == NULL || param == NULL || iface->code_exec == NULL) {
        return -1;
    }
    
    printf("[VULN_INTERFACE] 通过接口调用代码执行, 参数: %s\n", param);
    
    // 通过接口函数指针调用实现
    return iface->code_exec(iface->impl_data, param);
}

/**
 * 接口通用工具函数：SSRF
 * 漏洞点：通过接口函数指针调用实现
 */
int vuln_interface_ssrf(VulnInterface *iface, const char *param) {
    if (iface == NULL || param == NULL || iface->ssrf == NULL) {
        return -1;
    }
    
    printf("[VULN_INTERFACE] 通过接口调用SSRF, 参数: %s\n", param);
    
    // 通过接口函数指针调用实现
    return iface->ssrf(iface->impl_data, param);
}

/**
 * 接口析构
 */
void vuln_interface_destroy(VulnInterface *iface) {
    if (iface == NULL) {
        return;
    }
    
    printf("[VULN_INTERFACE] 销毁接口结构体\n");
    // 清理资源（此处无需特殊清理）
}