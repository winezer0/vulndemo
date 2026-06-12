/**
 * cmd_exec/interface_impl.c - 命令执行漏洞测试（接口实现类调用）
 * 测试通过接口调用命令执行实现
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../interface/vuln_interface.h"
#include "../common/vuln_parent.h"

/**
 * 命令执行接口实现结构体
 * 包含接口和实现数据
 */
typedef struct CmdExecImpl {
    VulnInterface interface;  // 接口结构体
    VulnParent parent;        // 实现数据（父结构体）
} CmdExecImpl;

/**
 * 命令执行接口实现函数
 */
static int cmd_exec_impl_func(void *impl, const char *param) {
    if (impl == NULL || param == NULL) {
        return -1;
    }
    
    CmdExecImpl *impl_data = (CmdExecImpl *)impl;
    printf("[CMD_EXEC_IMPL] 接口实现被调用, 参数: %s\n", param);
    
    // 通过实现数据调用父结构体的命令执行方法
    return impl_data->parent.cmd_exec(&impl_data->parent, param);
}

/**
 * 初始化命令执行接口实现
 */
static void cmd_exec_impl_init(CmdExecImpl *impl, const char *name, int id) {
    if (impl == NULL) {
        return;
    }
    
    // 初始化父结构体
    vuln_parent_init(&impl->parent, name, id);
    
    // 初始化接口，指向本实现函数
    vuln_interface_init(&impl->interface, cmd_exec_impl_func, NULL, NULL, impl);
    
    printf("[CMD_EXEC_IMPL] 初始化命令执行接口实现\n");
}

/**
 * 命令执行漏洞测试 - 接口实现类调用
 * 调用链：HTTP参数 -> 测试函数 -> 接口 -> 接口实现 -> 父结构体 -> 命令执行漏洞
 */
int cmd_exec_interface_test(const char *param) {
    printf("[CMD_EXEC_INTERFACE] 开始命令执行漏洞测试（接口实现类调用）\n");
    
    // 创建接口实现实例
    CmdExecImpl impl;
    cmd_exec_impl_init(&impl, "CmdExecInterfaceParent", 5);
    
    // 通过接口调用命令执行
    int result = vuln_interface_cmd_exec(&impl.interface, param);
    
    // 销毁实现
    vuln_interface_destroy(&impl.interface);
    vuln_parent_destroy(&impl.parent);
    
    printf("[CMD_EXEC_INTERFACE] 测试完成，结果: %d\n", result);
    return result;
}

/**
 * 命令执行漏洞测试 - 接口硬编码参数（无外部输入）
 */
int cmd_exec_interface_hardcoded(void) {
    printf("[CMD_EXEC_INTERFACE] 开始命令执行漏洞测试（接口硬编码参数）\n");
    
    // 创建接口实现实例
    CmdExecImpl impl;
    cmd_exec_impl_init(&impl, "HardcodedInterfaceParent", 6);
    
    // 使用硬编码参数通过接口调用
    int result = vuln_interface_cmd_exec(&impl.interface, "hardcoded_interface_command");
    
    // 销毁实现
    vuln_interface_destroy(&impl.interface);
    vuln_parent_destroy(&impl.parent);
    
    printf("[CMD_EXEC_INTERFACE] 硬编码测试完成，结果: %d\n", result);
    return result;
}