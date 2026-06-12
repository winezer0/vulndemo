package cmd_exec

import traits.VulnTrait

/**
 * 命令执行特质混入类
 * 测试特质混入调用链
 */
class TraitImpl implements VulnTrait {
    
    /**
     * 测试方法 - 调用特质命令执行漏洞
     */
    void testTraitCmdExec() {
        // 调用特质硬编码命令执行
        traitCmdExecHardcoded()
        
        // 调用特质带参数命令执行（外部参数）
        traitCmdExecWithParam("trait test")
    }
}
