/**
 * vuln_parent.h - 公共父结构体定义
 * 定义包含所有漏洞核心方法的父结构体
 */

#ifndef VULN_PARENT_H
#define VULN_PARENT_H

/**
 * 父结构体 - 包含漏洞核心方法
 * 用于模拟面向对象中的基类
 */
typedef struct VulnParent {
    // 方法函数指针
    int (*cmd_exec)(struct VulnParent *self, const char *param);
    int (*code_exec)(struct VulnParent *self, const char *param);
    int (*ssrf)(struct VulnParent *self, const char *param);
    
    // 数据成员
    char name[64];
    int id;
} VulnParent;

/**
 * 初始化父结构体
 * @param parent 父结构体指针
 * @param name 名称
 * @param id 标识符
 */
void vuln_parent_init(VulnParent *parent, const char *name, int id);

/**
 * 父结构体命令执行方法
 * @param self 结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_parent_cmd_exec(VulnParent *self, const char *param);

/**
 * 父结构体代码执行方法
 * @param self 结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_parent_code_exec(VulnParent *self, const char *param);

/**
 * 父结构体SSRF方法
 * @param self 结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_parent_ssrf(VulnParent *self, const char *param);

/**
 * 父结构体析构
 * @param parent 父结构体指针
 */
void vuln_parent_destroy(VulnParent *parent);

#endif // VULN_PARENT_H