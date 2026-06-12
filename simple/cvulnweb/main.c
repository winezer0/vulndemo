/**
 * main.c - 项目总入口，HTTP服务器启动和路由
 * 使用libmicrohttpd搭建HTTP服务器，解析请求参数并分发到各个漏洞测试
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <microhttpd.h>
#include "func_call.h"
#include "common/vuln_parent.h"
#include "common/vuln_child.h"
#include "interface/vuln_interface.h"

// 声明各测试函数
extern int cmd_exec_sub_struct_test(const char *param);
extern int cmd_exec_sub_struct_hardcoded(void);
extern int cmd_exec_interface_test(const char *param);
extern int cmd_exec_interface_hardcoded(void);
extern int code_exec_sub_struct_test(const char *param);
extern int code_exec_sub_struct_hardcoded(void);
extern int code_exec_interface_test(const char *param);
extern int code_exec_interface_hardcoded(void);
extern int ssrf_sub_struct_test(const char *param);
extern int ssrf_sub_struct_hardcoded(void);
extern int ssrf_interface_test(const char *param);
extern int ssrf_interface_hardcoded(void);

/**
 * 解析查询字符串参数
 * @param query_string 查询字符串（如 "cmd=ls&param=test"）
 * @param key 要查找的键名
 * @return 键对应的值，如果未找到返回NULL
 */
static char* parse_query_param(const char *query_string, const char *key) {
    if (query_string == NULL || key == NULL) {
        return NULL;
    }
    
    // 复制查询字符串，因为 strtok 会修改原字符串
    char *query_copy = strdup(query_string);
    if (query_copy == NULL) {
        return NULL;
    }
    
    char *token = strtok(query_copy, "&");
    while (token != NULL) {
        char *equals = strchr(token, '=');
        if (equals != NULL) {
            *equals = '\0';  // 分割键值对
            if (strcmp(token, key) == 0) {
                char *value = strdup(equals + 1);
                free(query_copy);
                return value;
            }
        }
        token = strtok(NULL, "&");
    }
    
    free(query_copy);
    return NULL;
}

/**
 * 发送HTTP响应
 * @param cls MHD回调的cls参数
 * @param connection MHD连接
 * @param url 请求URL
 * @param method HTTP方法
 * @param version HTTP版本
 * @param upload_data 上传数据
 * @param upload_data_size 上传数据大小
 * @param con_cls 连接特定数据
 * @return MHD响应结果
 */
static enum MHD_Result handle_request(void *cls,
                                      struct MHD_Connection *connection,
                                      const char *url,
                                      const char *method,
                                      const char *version,
                                      const char *upload_data,
                                      size_t *upload_data_size,
                                      void **con_cls) {
    static int first_call = 1;
    
    // 首次调用时初始化连接特定数据
    if (first_call) {
        first_call = 0;
        return MHD_YES;
    }
    
    // 忽略POST数据
    if (*upload_data_size > 0) {
        *upload_data_size = 0;
        return MHD_YES;
    }
    
    const char *query_string = MHD_lookup_connection_value(connection, MHD_GET_ARGUMENT_KIND, NULL);
    
    char response[4096] = {0};
    
    printf("[HTTP] 请求: %s %s?%s\n", method, url, query_string ? query_string : "");
    
    // 路由处理
    if (strcmp(url, "/") == 0) {
        // 首页 - 返回静态HTML
        FILE *fp = fopen("static/index.html", "r");
        if (fp) {
            fseek(fp, 0, SEEK_END);
            long size = ftell(fp);
            fseek(fp, 0, SEEK_SET);
            char *html = malloc(size + 1);
            if (html) {
                fread(html, 1, size, fp);
                html[size] = '\0';
                fclose(fp);
                
                struct MHD_Response *response = MHD_create_response_from_buffer(strlen(html), html, MHD_RESPMEM_MUST_FREE);
                MHD_add_response_header(response, "Content-Type", "text/html; charset=utf-8");
                enum MHD_Result ret = MHD_queue_response(connection, 200, response);
                MHD_destroy_response(response);
                return ret;
            }
            fclose(fp);
        }
        snprintf(response, sizeof(response), "无法加载首页");
    }
    else if (strcmp(url, "/cmd_exec_sub") == 0) {
        // 命令执行漏洞测试 - 子结构体继承调用
        char *cmd = parse_query_param(query_string, "cmd");
        if (cmd) {
            int result = cmd_exec_sub_struct_test(cmd);
            snprintf(response, sizeof(response), "命令执行测试完成，结果: %d", result);
            free(cmd);
        } else {
            snprintf(response, sizeof(response), "缺少cmd参数");
        }
    }
    else if (strcmp(url, "/cmd_exec_iface") == 0) {
        // 命令执行漏洞测试 - 接口实现类调用
        char *cmd = parse_query_param(query_string, "cmd");
        if (cmd) {
            int result = cmd_exec_interface_test(cmd);
            snprintf(response, sizeof(response), "接口命令执行测试完成，结果: %d", result);
            free(cmd);
        } else {
            snprintf(response, sizeof(response), "缺少cmd参数");
        }
    }
    else if (strcmp(url, "/cmd_exec_hardcoded") == 0) {
        // 命令执行漏洞测试 - 硬编码参数
        int result = cmd_exec_sub_struct_hardcoded();
        snprintf(response, sizeof(response), "硬编码命令执行测试完成，结果: %d", result);
    }
    else if (strcmp(url, "/code_exec_sub") == 0) {
        // 代码执行漏洞测试 - 子结构体继承调用
        char *code = parse_query_param(query_string, "code");
        if (code) {
            int result = code_exec_sub_struct_test(code);
            snprintf(response, sizeof(response), "代码执行测试完成，结果: %d", result);
            free(code);
        } else {
            snprintf(response, sizeof(response), "缺少code参数");
        }
    }
    else if (strcmp(url, "/code_exec_iface") == 0) {
        // 代码执行漏洞测试 - 接口实现类调用
        char *code = parse_query_param(query_string, "code");
        if (code) {
            int result = code_exec_interface_test(code);
            snprintf(response, sizeof(response), "接口代码执行测试完成，结果: %d", result);
            free(code);
        } else {
            snprintf(response, sizeof(response), "缺少code参数");
        }
    }
    else if (strcmp(url, "/code_exec_hardcoded") == 0) {
        // 代码执行漏洞测试 - 硬编码参数
        int result = code_exec_sub_struct_hardcoded();
        snprintf(response, sizeof(response), "硬编码代码执行测试完成，结果: %d", result);
    }
    else if (strcmp(url, "/ssrf_sub") == 0) {
        // SSRF漏洞测试 - 子结构体继承调用
        char *url_param = parse_query_param(query_string, "url");
        if (url_param) {
            int result = ssrf_sub_struct_test(url_param);
            snprintf(response, sizeof(response), "SSRF测试完成，结果: %d", result);
            free(url_param);
        } else {
            snprintf(response, sizeof(response), "缺少url参数");
        }
    }
    else if (strcmp(url, "/ssrf_iface") == 0) {
        // SSRF漏洞测试 - 接口实现类调用
        char *url_param = parse_query_param(query_string, "url");
        if (url_param) {
            int result = ssrf_interface_test(url_param);
            snprintf(response, sizeof(response), "接口SSRF测试完成，结果: %d", result);
            free(url_param);
        } else {
            snprintf(response, sizeof(response), "缺少url参数");
        }
    }
    else if (strcmp(url, "/ssrf_hardcoded") == 0) {
        // SSRF漏洞测试 - 硬编码参数
        int result = ssrf_sub_struct_hardcoded();
        snprintf(response, sizeof(response), "硬编码SSRF测试完成，结果: %d", result);
    }
    else if (strcmp(url, "/func_cmd") == 0) {
        // 跨文件函数调用 - 命令执行
        char *cmd = parse_query_param(query_string, "cmd");
        if (cmd) {
            int result = execute_command(cmd);
            snprintf(response, sizeof(response), "函数调用命令执行完成，结果: %d", result);
            free(cmd);
        } else {
            snprintf(response, sizeof(response), "缺少cmd参数");
        }
    }
    else if (strcmp(url, "/func_code") == 0) {
        // 跨文件函数调用 - 代码执行
        char *code = parse_query_param(query_string, "code");
        if (code) {
            int result = execute_code(code);
            snprintf(response, sizeof(response), "函数调用代码执行完成，结果: %d", result);
            free(code);
        } else {
            snprintf(response, sizeof(response), "缺少code参数");
        }
    }
    else if (strcmp(url, "/func_ssrf") == 0) {
        // 跨文件函数调用 - SSRF
        char *url_param = parse_query_param(query_string, "url");
        if (url_param) {
            int result = perform_request(url_param);
            snprintf(response, sizeof(response), "函数调用SSRF完成，结果: %d", result);
            free(url_param);
        } else {
            snprintf(response, sizeof(response), "缺少url参数");
        }
    }
    else {
        // 404页面
        snprintf(response, sizeof(response), "404 - 页面未找到: %s", url);
    }
    
    // 发送响应
    struct MHD_Response *mhd_response = MHD_create_response_from_buffer(strlen(response), response, MHD_RESPMEM_MUST_COPY);
    MHD_add_response_header(mhd_response, "Content-Type", "text/plain; charset=utf-8");
    enum MHD_Result ret = MHD_queue_response(connection, 200, mhd_response);
    MHD_destroy_response(mhd_response);
    
    return ret;
}

/**
 * 主函数 - 启动HTTP服务器
 */
int main(int argc, char *argv[]) {
    printf("=== C语言Web漏洞测试平台 ===\n");
    printf("使用libmicrohttpd作为HTTP服务器\n");
    printf("模拟面向对象：结构体继承、函数指针接口、多层调用链\n\n");
    
    // 默认端口
    uint16_t port = 8080;
    if (argc > 1) {
        port = (uint16_t)atoi(argv[1]);
    }
    
    printf("服务器启动在端口: %d\n", port);
    printf("访问 http://localhost:%d/ 查看测试页面\n", port);
    printf("按 Ctrl+C 停止服务器\n\n");
    
    // 启动HTTP服务器
    struct MHD_Daemon *daemon = MHD_start_daemon(MHD_USE_INTERNAL_POLLING_THREAD, port, NULL, NULL, &handle_request, NULL, MHD_OPTION_END);
    
    if (daemon == NULL) {
        fprintf(stderr, "无法启动HTTP服务器\n");
        return 1;
    }
    
    // 等待用户中断
    getchar();
    
    // 清理
    MHD_stop_daemon(daemon);
    printf("服务器已停止\n");
    
    return 0;
}