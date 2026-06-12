from common.vuln_child import VulnChild

class CodeExecSubClassTest(VulnChild):
    def execute(self, code):
        # 代码执行漏洞测试：子类继承VulnChild，调用父类code_exec方法
        # 调用链：外部参数code -> CodeExecSubClassTest -> VulnChild -> VulnParent.code_exec -> eval
        # 漏洞点：eval执行用户控制的code参数
        return self.code_exec(code)
    
    def hardcoded_test(self):
        # 代码执行漏洞测试：硬编码场景，无外部参数
        # 调用链：hardcoded_test -> VulnChild -> VulnParent.hardcoded_code_exec -> eval
        return self.hardcoded_code_exec()