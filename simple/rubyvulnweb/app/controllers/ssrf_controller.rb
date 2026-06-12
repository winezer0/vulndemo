require_relative '../models/common/vuln_parent'
require_relative '../models/common/vuln_child'
require_relative '../models/ssrf/sub_class_test'
require_relative '../models/ssrf/module_impl'
require 'net/http'
require 'uri'

class SsrfController < ApplicationController
  # SSRF测试控制器

  def fetch_url
    url = params[:url] || 'http://127.0.0.1'
    # 漏洞点：直接使用外部参数发起HTTP请求
    uri = URI.parse(url)
    response = Net::HTTP.get(uri)
    render plain: "SSRF响应内容: #{response}"
  end

  def child_fetch
    url = params[:url] || 'http://127.0.0.1'
    # 通过子类调用父类漏洞方法，形成 外部参数 → 子类 → 父类 → Net::HTTP 调用链
    child = SsrfSubClass.new(url)
    result = child.execute_from_parent
    render plain: "子类继承调用结果: #{result}"
  end

  def module_fetch
    url = params[:url] || 'http://127.0.0.1'
    # 通过模块包含类调用漏洞方法
    impl = SsrfModuleImpl.new(url)
    result = impl.module_execute
    render plain: "模块混入调用结果: #{result}"
  end

  def hardcoded_ssrf
    parent = VulnParent.new
    result = parent.vuln_ssrf_hardcoded
    render plain: "硬编码SSRF结果: #{result}"
  end
end
