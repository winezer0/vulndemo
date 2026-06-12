package groovyvulnweb

import common.VulnChild
import ssrf.SubClassTest
import ssrf.TraitImpl
import utils.FuncCall

/**
 * SSRF测试控制器
 * 处理SSRF相关的测试请求
 */
class SsrfController {
    
    /**
     * 首页 - 显示所有测试链接
     */
    def index() {
        render view: '/index'
    }
    
    /**
     * 子类继承测试
     * 测试链路：外部参数 → 控制器 → 子类 → 父类 → 漏洞点
     */
    def subClassTest() {
        String userInput = params.url ?: "http://example.com/test"
        
        // 创建子类实例
        SubClassTest subClass = new SubClassTest()
        
        // 调用子类方法（内部调用父类漏洞）
        subClass.testSsrf()
        subClass.testChildSsrf()
        
        // 调用父类带参数漏洞方法（外部参数）
        subClass.ssrfWithParam(userInput)
        
        render "SubClass Test Completed - URL: ${userInput}"
    }
    
    /**
     * 特质混入测试
     * 测试链路：外部参数 → 控制器 → 特质实现类 → 特质方法 → 漏洞点
     */
    def traitImplTest() {
        String userInput = params.url ?: "http://example.com/test"
        
        // 创建特质实现类实例
        TraitImpl traitImpl = new TraitImpl()
        
        // 调用特质方法
        traitImpl.testTraitSsrf()
        
        // 调用特质带参数漏洞方法（外部参数）
        traitImpl.traitSsrfWithParam(userInput)
        
        render "TraitImpl Test Completed - URL: ${userInput}"
    }
    
    /**
     * 直接调用测试
     * 测试链路：外部参数 → 控制器 → 工具类函数 → 漏洞点
     */
    def directTest() {
        String userInput = params.url ?: "http://example.com/test"
        
        // 直接调用工具类函数
        FuncCall.ssrfFunction()
        FuncCall.ssrfFunctionWithParam(userInput)
        
        render "Direct Test Completed - URL: ${userInput}"
    }
}
