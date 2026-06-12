/**
 * func_call.h - 跨文件函数调用头文件
 * 定义通用函数接口，用于硬编码漏洞测试和跨文件调用
 */

#ifndef FUNC_CALL_H
#define FUNC_CALL_H

/**
 * 执行命令（使用 system()）
 * @param cmd 命令字符串
 * @return 命令执行结果
 */
int execute_command(const char *cmd);

/**
 * 执行代码（使用 dlsym 模拟）
 * @param code 代码字符串
 * @return 执行结果
 */
int execute_code(const char *code);

/**
 * 发起网络请求（使用 libcurl）
 * @param url 目标URL
 * @return 请求结果
 */
int perform_request(const char *url);

/**
 * 硬编码命令执行漏洞点（无外部输入）
 */
void hardcoded_cmd_exec(void);

/**
 * 硬编码代码执行漏洞点（无外部输入）
 */
void hardcoded_code_exec(void);

/**
 * 硬编码SSRF漏洞点（无外部输入）
 */
void hardcoded_ssrf(void);

#endif // FUNC_CALL_H