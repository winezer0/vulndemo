package code_exec

import common.VulnParent

/**
 * 代码执行子类测试 - 继承自 VulnParent
 * 测试子类继承调用链
 */
class SubClassTest extends VulnParent {
    
    /**
     * 测试方法 - 调用父类代码执行漏洞
     */
    void testCodeExec() {
        // 调用父类硬编码代码执行
        codeExecHardcoded()
        
        // 调用父类带参数代码执行（外部参数）
        codeExecWithParam("3 * 3")
    }
    
    /**
     * 测试方法 - 子类特有代码执行
     */
    void testChildCodeExec() {
        // 调用子类方法（子类方法调用父类漏洞）
        childCodeExec()
        childCodeExecWithParam("4 * 4")
    }
}
