/**
 * vuln_interface.h - 独立接口定义文件
 * 定义函数指针结构体，模拟面向对象中的接口
 */

#ifndef VULN_INTERFACE_H
#define VULN_INTERFACE_H

/**
 * 接口结构体 - 包含函数指针
 * 用于模拟面向对象中的接口
 */
typedef struct VulnInterface {
    // 接口方法函数指针
    int (*cmd_exec)(void *impl, const char *param);
    int (*code_exec)(void *impl, const char *param);
    int (*ssrf)(void *impl, const char *param);
    
    // 实现数据（泛型指针）
    void *impl_data;
} VulnInterface;

/**
 * 初始化接口结构体
 * @param iface 接口指针
 * @param cmd_exec_func 命令执行函数指针
 * @param code_exec_func 代码执行函数指针
 * @param ssrf_func SSRF函数指针
 * @param impl_data 实现数据指针
 */
void vuln_interface_init(VulnInterface *iface,
                         int (*cmd_exec_func)(void*, const char*),
                         int (*code_exec_func)(void*, const char*),
                         int (*ssrf_func)(void*, const char*),
                         void *impl_data);

/**
 * 接口通用工具函数：执行命令
 * @param iface 接口指针
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_interface_cmd_exec(VulnInterface *iface, const char *param);

/**
 * 接口通用工具函数：执行代码
 * @param iface 接口指针
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_interface_code_exec(VulnInterface *iface, const char *param);

/**
 * 接口通用工具函数：SSRF
 * @param iface 接口指针
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_interface_ssrf(VulnInterface *iface, const char *param);

/**
 * 接口析构
 * @param iface 接口指针
 */
void vuln_interface_destroy(VulnInterface *iface);

#endif // VULN_INTERFACE_H