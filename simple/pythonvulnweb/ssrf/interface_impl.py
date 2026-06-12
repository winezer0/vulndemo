from interface.vuln_interface import VulnInterface
from common.vuln_parent import VulnParent

class SsrfInterfaceImpl(VulnInterface, VulnParent):
    def execute(self, url):
        # SSRF漏洞测试：接口实现类，继承父类并实现抽象方法
        # 调用链：外部参数url -> execute方法 -> 父类ssrf -> requests.get
        # 漏洞点：requests.get获取用户控制的url
        return self.ssrf(url)
    
    def hardcoded_execute(self):
        # SSRF漏洞测试：接口实现类，硬编码场景
        # 调用链：hardcoded_execute方法 -> 父类hardcoded_ssrf -> requests.get
        return self.hardcoded_ssrf()