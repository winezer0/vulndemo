require_relative '../concerns/vuln_module'
require 'net/http'
require 'uri'

class SsrfModuleImpl
  # SSRF模块包含测试
  # 调用链：外部参数 → 模块包含类 → VulnModule → Net::HTTP

  include VulnModule

  def initialize(url = nil)
    @module_input = url
  end

  def module_execute
    # 漏洞点：通过模块混入调用SSRF方法
    module_ssrf
  end

  def module_hardcoded_execute
    # 漏洞点：通过模块混入调用硬编码SSRF方法
    module_ssrf_hardcoded
  end
end
