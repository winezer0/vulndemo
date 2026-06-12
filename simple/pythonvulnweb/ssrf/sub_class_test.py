from common.vuln_child import VulnChild

class SsrfSubClassTest(VulnChild):
    def execute(self, url):
        # SSRF漏洞测试：子类继承VulnChild，调用父类ssrf方法
        # 调用链：外部参数url -> SsrfSubClassTest -> VulnChild -> VulnParent.ssrf -> requests.get
        # 漏洞点：requests.get获取用户控制的url
        return self.ssrf(url)
    
    def hardcoded_test(self):
        # SSRF漏洞测试：硬编码场景，无外部参数
        # 调用链：hardcoded_test -> VulnChild -> VulnParent.hardcoded_ssrf -> requests.get
        return self.hardcoded_ssrf()