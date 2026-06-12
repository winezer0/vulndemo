/**
 * func_call.c - 跨文件函数调用实现
 * 实现通用函数接口，包含硬编码漏洞点
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curl/curl.h>
#include "func_call.h"

// Windows兼容性：popen/pclose在MSVC中名为_popen/_pclose
#ifdef _WIN32
#include <io.h>
#define popen _popen
#define pclose _pclose
#endif

/**
 * 执行命令 - 使用 system() 执行系统命令
 * 漏洞类型：命令注入
 */
int execute_command(const char *cmd) {
    if (cmd == NULL) {
        return -1;
    }
    // 漏洞点：直接使用 system() 执行用户输入的命令，可能导致命令注入
    printf("[CMD_EXEC] 执行命令: %s\n", cmd);
    int ret = system(cmd);
    return ret;
}

/**
 * 执行代码 - 使用 popen() 执行代码
 * 漏洞类型：代码执行
 */
int execute_code(const char *code) {
    if (code == NULL) {
        return -1;
    }
    
    // 漏洞点：使用 popen 执行用户提供的代码
    printf("[CODE_EXEC] 执行代码: %s\n", code);
    
    // 使用 popen 执行代码作为脚本
    char cmd[1024];
#ifdef _WIN32
    snprintf(cmd, sizeof(cmd), "python -c \"%s\"", code);
    FILE *pipe = _popen(cmd, "r");
#else
    snprintf(cmd, sizeof(cmd), "python3 -c \"%s\"", code);
    FILE *pipe = popen(cmd, "r");
#endif
    
    if (pipe == NULL) {
        return -1;
    }
    
    // 读取输出
    char buffer[256];
    while (fgets(buffer, sizeof(buffer), pipe) != NULL) {
        printf("[CODE_EXEC_OUTPUT] %s", buffer);
    }
    
#ifdef _WIN32
    int ret = _pclose(pipe);
#else
    int ret = pclose(pipe);
#endif
    
    return ret;
}

/**
 * 发起网络请求 - 使用 libcurl
 * 漏洞类型：SSRF（服务器端请求伪造）
 */
int perform_request(const char *url) {
    if (url == NULL) {
        return -1;
    }
    
    // 漏洞点：直接请求用户指定的URL，可能导致SSRF攻击
    printf("[SSRF] 请求URL: %s\n", url);
    
    CURL *curl;
    CURLcode res;
    long response_code;
    
    curl_global_init(CURL_GLOBAL_DEFAULT);
    curl = curl_easy_init();
    if(curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url);
        curl_easy_setopt(curl, CURLOPT_NOBODY, 1L); // 只获取头部
        res = curl_easy_perform(curl);
        if(res != CURLE_OK) {
            fprintf(stderr, "[SSRF] 请求失败: %s\n", curl_easy_strerror(res));
            curl_easy_cleanup(curl);
            curl_global_cleanup();
            return -1;
        }
        curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
        printf("[SSRF] 响应码: %ld\n", response_code);
        curl_easy_cleanup(curl);
    }
    curl_global_cleanup();
    return 0;
}

/**
 * 硬编码命令执行漏洞点
 * 无外部输入，使用固定命令
 */
void hardcoded_cmd_exec(void) {
    // 漏洞点：硬编码危险命令
    printf("[HARDCODED] 执行硬编码命令: echo hardcoded_command\n");
    FILE *pipe = popen("echo hardcoded_command", "r");
    if (pipe != NULL) {
        char buffer[256];
        while (fgets(buffer, sizeof(buffer), pipe) != NULL) {
            printf("[HARDCODED_OUTPUT] %s", buffer);
        }
        pclose(pipe);
    }
}

/**
 * 硬编码代码执行漏洞点
 * 无外部输入，使用固定代码
 */
void hardcoded_code_exec(void) {
    // 漏洞点：硬编码执行代码
    printf("[HARDCODED] 执行硬编码代码: print('hardcoded code execution')\n");
#ifdef _WIN32
    FILE *pipe = _popen("python -c \"print('hardcoded code execution')\"", "r");
#else
    FILE *pipe = popen("python3 -c \"print('hardcoded code execution')\"", "r");
#endif
    if (pipe != NULL) {
        char buffer[256];
        while (fgets(buffer, sizeof(buffer), pipe) != NULL) {
            printf("[HARDCODED_OUTPUT] %s", buffer);
        }
#ifdef _WIN32
        _pclose(pipe);
#else
        pclose(pipe);
#endif
    }
}

/**
 * 硬编码SSRF漏洞点
 * 无外部输入，使用固定URL
 */
void hardcoded_ssrf(void) {
    // 漏洞点：硬编码请求内网地址
    printf("[HARDCODED] 请求硬编码URL: http://192.168.1.1\n");
    perform_request("http://192.168.1.1");
}