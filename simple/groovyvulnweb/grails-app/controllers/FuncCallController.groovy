package groovyvulnweb

import common.VulnParent
import common.VulnChild
import utils.FuncCall

/**
 * 跨文件调用测试控制器
 * 处理跨文件调用相关的测试请求
 */
class FuncCallController {
    
    /**
     * 首页 - 显示所有测试链接
     */
    def index() {
        // 渲染主页面，包含所有测试链接
        render view: '/index'
    }
    
    /**
     * 测试方法 - 演示跨文件调用
     */
    def test() {
        // 1. 直接调用工具类静态方法
        FuncCall.cmdExecFunction()
        FuncCall.codeExecFunction()
        FuncCall.ssrfFunction()
        
        // 2. 创建父类实例并调用
        VulnParent parent = new VulnParent()
        parent.cmdExecHardcoded()
        parent.codeExecHardcoded()
        parent.ssrfHardcoded()
        
        // 3. 创建子类实例并调用（继承链）
        VulnChild child = new VulnChild()
        child.childCmdExec()
        child.childCodeExec()
        child.childSsrf()
        
        render "FuncCall Test Completed"
    }
}
