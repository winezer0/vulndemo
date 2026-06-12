package code_exec

import traits.VulnTrait

/**
 * 代码执行特质混入类
 * 测试特质混入调用链
 */
class TraitImpl implements VulnTrait {
    
    /**
     * 测试方法 - 调用特质代码执行漏洞
     */
    void testTraitCodeExec() {
        // 调用特质硬编码代码执行
        traitCodeExecHardcoded()
        
        // 调用特质带参数代码执行（外部参数）
        traitCodeExecWithParam("5 * 5")
    }
}
