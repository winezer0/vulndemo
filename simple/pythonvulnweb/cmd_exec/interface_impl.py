from interface.vuln_interface import VulnInterface
from common.vuln_parent import VulnParent

class CmdExecInterfaceImpl(VulnInterface, VulnParent):
    def execute(self, cmd):
        # 命令执行漏洞测试：接口实现类，继承父类并实现抽象方法
        # 调用链：外部参数cmd -> execute方法 -> 父类cmd_exec -> os.system
        # 漏洞点：os.system执行用户控制的cmd参数
        return self.cmd_exec(cmd)
    
    def hardcoded_execute(self):
        # 命令执行漏洞测试：接口实现类，硬编码场景
        # 调用链：hardcoded_execute方法 -> 父类hardcoded_cmd_exec -> os.system
        return self.hardcoded_cmd_exec()