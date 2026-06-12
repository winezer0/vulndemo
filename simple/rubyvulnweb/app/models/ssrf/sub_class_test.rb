require_relative '../common/vuln_child'
require 'net/http'
require 'uri'

class SsrfSubClass < VulnChild
  # SSRF子类继承测试
  # 调用链：外部参数 → 子类 → 父类VulnParent → Net::HTTP

  def initialize(url = nil)
    super(url)
  end

  def execute_from_parent
    # 漏洞点：继承调用父类SSRF方法
    vuln_ssrf
  end

  def execute_hardcoded
    # 漏洞点：继承调用父类硬编码SSRF方法
    vuln_ssrf_hardcoded
  end
end
