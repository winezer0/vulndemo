package ssrf

import traits.VulnTrait

/**
 * SSRF特质混入类
 * 测试特质混入调用链
 */
class TraitImpl implements VulnTrait {
    
    /**
     * 测试方法 - 调用特质SSRF漏洞
     */
    void testTraitSsrf() {
        // 调用特质硬编码SSRF
        traitSsrfHardcoded()
        
        // 调用特质带参数SSRF（外部参数）
        traitSsrfWithParam("http://example.com/trait")
    }
}
