from common.vuln_parent import VulnParent

class VulnChild(VulnParent):
    def execute_cmd(self, cmd):
        # 子类调用父类命令执行方法：形成调用链
        # 调用链：外部参数 -> 子类方法 -> 父类cmd_exec -> os.system
        return self.cmd_exec(cmd)
    
    def execute_code(self, code):
        # 子类调用父类代码执行方法：形成调用链
        # 调用链：外部参数 -> 子类方法 -> 父类code_exec -> eval
        return self.code_exec(code)
    
    def execute_ssrf(self, url):
        # 子类调用父类SSRF方法：形成调用链
        # 调用链：外部参数 -> 子类方法 -> 父类ssrf -> requests.get
        return self.ssrf(url)
    
    def execute_hardcoded(self):
        # 子类调用父类硬编码漏洞方法：无外部参数
        # 调用链：子类方法 -> 父类hardcoded_cmd_exec -> os.system
        result_cmd = self.hardcoded_cmd_exec()
        result_code = self.hardcoded_code_exec()
        result_ssrf = self.hardcoded_ssrf()
        return f"Hardcoded results:\nCMD: {result_cmd}\nCODE: {result_code}\nSSRF: {result_ssrf}"