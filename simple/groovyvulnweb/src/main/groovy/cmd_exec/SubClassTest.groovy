package cmd_exec

import common.VulnParent

/**
 * 命令执行子类测试 - 继承自 VulnParent
 * 测试子类继承调用链
 */
class SubClassTest extends VulnParent {
    
    /**
     * 测试方法 - 调用父类命令执行漏洞
     */
    void testCmdExec() {
        // 调用父类硬编码命令执行
        cmdExecHardcoded()
        
        // 调用父类带参数命令执行（外部参数）
        cmdExecWithParam("test")
    }
    
    /**
     * 测试方法 - 子类特有命令执行
     */
    void testChildCmdExec() {
        // 调用子类方法（子类方法调用父类漏洞）
        childCmdExec()
        childCmdExecWithParam("child test")
    }
}
