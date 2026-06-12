from interface.vuln_interface import VulnInterface
from common.vuln_parent import VulnParent

class CodeExecInterfaceImpl(VulnInterface, VulnParent):
    def execute(self, code):
        # 代码执行漏洞测试：接口实现类，继承父类并实现抽象方法
        # 调用链：外部参数code -> execute方法 -> 父类code_exec -> eval
        # 漏洞点：eval执行用户控制的code参数
        return self.code_exec(code)
    
    def hardcoded_execute(self):
        # 代码执行漏洞测试：接口实现类，硬编码场景
        # 调用链：hardcoded_execute方法 -> 父类hardcoded_code_exec -> eval
        return self.hardcoded_code_exec()