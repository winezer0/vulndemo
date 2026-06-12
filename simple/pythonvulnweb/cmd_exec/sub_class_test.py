from common.vuln_child import VulnChild

class CmdExecSubClassTest(VulnChild):
    def execute(self, cmd):
        # 命令执行漏洞测试：子类继承VulnChild，调用父类cmd_exec方法
        # 调用链：外部参数cmd -> CmdExecSubClassTest -> VulnChild -> VulnParent.cmd_exec -> os.system
        # 漏洞点：os.system执行用户控制的cmd参数
        return self.cmd_exec(cmd)
    
    def hardcoded_test(self):
        # 命令执行漏洞测试：硬编码场景，无外部参数
        # 调用链：hardcoded_test -> VulnChild -> VulnParent.hardcoded_cmd_exec -> os.system
        return self.hardcoded_cmd_exec()