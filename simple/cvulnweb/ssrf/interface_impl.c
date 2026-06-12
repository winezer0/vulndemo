/**
 * ssrf/interface_impl.c - SSRF漏洞测试（接口实现类调用）
 * 测试通过接口调用SSRF实现
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../interface/vuln_interface.h"
#include "../common/vuln_parent.h"

/**
 * SSRF接口实现结构体
 * 包含接口和实现数据
 */
typedef struct SSRFImpl {
    VulnInterface interface;  // 接口结构体
    VulnParent parent;        // 实现数据（父结构体）
} SSRFImpl;

/**
 * SSRF接口实现函数
 */
static int ssrf_impl_func(void *impl, const char *param) {
    if (impl == NULL || param == NULL) {
        return -1;
    }
    
    SSRFImpl *impl_data = (SSRFImpl *)impl;
    printf("[SSRF_IMPL] 接口实现被调用, 参数: %s\n", param);
    
    // 通过实现数据调用父结构体的SSRF方法
    return impl_data->parent.ssrf(&impl_data->parent, param);
}

/**
 * 初始化SSRF接口实现
 */
static void ssrf_impl_init(SSRFImpl *impl, const char *name, int id) {
    if (impl == NULL) {
        return;
    }
    
    // 初始化父结构体
    vuln_parent_init(&impl->parent, name, id);
    
    // 初始化接口，指向本实现函数
    vuln_interface_init(&impl->interface, NULL, NULL, ssrf_impl_func, impl);
    
    printf("[SSRF_IMPL] 初始化SSRF接口实现\n");
}

/**
 * SSRF漏洞测试 - 接口实现类调用
 * 调用链：HTTP参数 -> 测试函数 -> 接口 -> 接口实现 -> 父结构体 -> SSRF漏洞
 */
int ssrf_interface_test(const char *param) {
    printf("[SSRF_INTERFACE] 开始SSRF漏洞测试（接口实现类调用）\n");
    
    // 创建接口实现实例
    SSRFImpl impl;
    ssrf_impl_init(&impl, "SSRFInterfaceParent", 17);
    
    // 通过接口调用SSRF
    int result = vuln_interface_ssrf(&impl.interface, param);
    
    // 销毁实现
    vuln_interface_destroy(&impl.interface);
    vuln_parent_destroy(&impl.parent);
    
    printf("[SSRF_INTERFACE] 测试完成，结果: %d\n", result);
    return result;
}

/**
 * SSRF漏洞测试 - 接口硬编码参数（无外部输入）
 */
int ssrf_interface_hardcoded(void) {
    printf("[SSRF_INTERFACE] 开始SSRF漏洞测试（接口硬编码参数）\n");
    
    // 创建接口实现实例
    SSRFImpl impl;
    ssrf_impl_init(&impl, "HardcodedSSRFInterfaceParent", 18);
    
    // 使用硬编码参数通过接口调用
    int result = vuln_interface_ssrf(&impl.interface, "http://10.0.0.1:3000");
    
    // 销毁实现
    vuln_interface_destroy(&impl.interface);
    vuln_parent_destroy(&impl.parent);
    
    printf("[SSRF_INTERFACE] 硬编码测试完成，结果: %d\n", result);
    return result;
}