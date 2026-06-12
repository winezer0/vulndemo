/**
 * vuln_child.h - 独立子结构体定义
 * 包含 VulnParent 作为成员，模拟继承关系
 */

#ifndef VULN_CHILD_H
#define VULN_CHILD_H

#include "vuln_parent.h"

/**
 * 子结构体 - 包含父结构体
 * 用于模拟面向对象中的继承关系
 */
typedef struct VulnChild {
    VulnParent parent;  // 包含父结构体，模拟继承
    
    // 子结构体特有数据
    char child_name[64];
    int child_id;
} VulnChild;

/**
 * 初始化子结构体
 * @param child 子结构体指针
 * @param parent_name 父结构体名称
 * @param parent_id 父结构体ID
 * @param child_name 子结构体名称
 * @param child_id 子结构体ID
 */
void vuln_child_init(VulnChild *child, const char *parent_name, int parent_id,
                     const char *child_name, int child_id);

/**
 * 子结构体调用父结构体命令执行方法
 * @param child 子结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_child_cmd_exec(VulnChild *child, const char *param);

/**
 * 子结构体调用父结构体代码执行方法
 * @param child 子结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_child_code_exec(VulnChild *child, const char *param);

/**
 * 子结构体调用父结构体SSRF方法
 * @param child 子结构体自身
 * @param param 输入参数
 * @return 执行结果
 */
int vuln_child_ssrf(VulnChild *child, const char *param);

/**
 * 子结构体析构
 * @param child 子结构体指针
 */
void vuln_child_destroy(VulnChild *child);

#endif // VULN_CHILD_H