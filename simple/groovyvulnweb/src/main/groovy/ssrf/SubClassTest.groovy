package ssrf

import common.VulnParent

/**
 * SSRF子类测试 - 继承自 VulnParent
 * 测试子类继承调用链
 */
class SubClassTest extends VulnParent {
    
    /**
     * 测试方法 - 调用父类SSRF漏洞
     */
    void testSsrf() {
        // 调用父类硬编码SSRF
        ssrfHardcoded()
        
        // 调用父类带参数SSRF（外部参数）
        ssrfWithParam("http://example.com/test")
    }
    
    /**
     * 测试方法 - 子类特有SSRF
     */
    void testChildSsrf() {
        // 调用子类方法（子类方法调用父类漏洞）
        childSsrf()
        childSsrfWithParam("http://example.com/child")
    }
}
