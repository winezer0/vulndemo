package groovyvulnweb

import common.VulnChild
import code_exec.SubClassTest
import code_exec.TraitImpl
import utils.FuncCall

/**
 * 代码执行测试控制器
 * 处理代码执行相关的测试请求
 */
class CodeExecController {
    
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
        String userInput = params.code ?: "2 + 2"
        
        // 创建子类实例
        SubClassTest subClass = new SubClassTest()
        
        // 调用子类方法（内部调用父类漏洞）
        subClass.testCodeExec()
        subClass.testChildCodeExec()
        
        // 调用父类带参数漏洞方法（外部参数）
        subClass.codeExecWithParam(userInput)
        
        render "SubClass Test Completed - Code: ${userInput}"
    }
    
    /**
     * 特质混入测试
     * 测试链路：外部参数 → 控制器 → 特质实现类 → 特质方法 → 漏洞点
     */
    def traitImplTest() {
        String userInput = params.code ?: "2 + 2"
        
        // 创建特质实现类实例
        TraitImpl traitImpl = new TraitImpl()
        
        // 调用特质方法
        traitImpl.testTraitCodeExec()
        
        // 调用特质带参数漏洞方法（外部参数）
        traitImpl.traitCodeExecWithParam(userInput)
        
        render "TraitImpl Test Completed - Code: ${userInput}"
    }
    
    /**
     * 直接调用测试
     * 测试链路：外部参数 → 控制器 → 工具类函数 → 漏洞点
     */
    def directTest() {
        String userInput = params.code ?: "2 + 2"
        
        // 直接调用工具类函数
        FuncCall.codeExecFunction()
        FuncCall.codeExecFunctionWithParam(userInput)
        
        render "Direct Test Completed - Code: ${userInput}"
    }
}
