require 'net/http'
require 'uri'

module VulnModule
  # 漏洞模块定义，提供各类漏洞的mixin能力
  # 通过 include 混入到类中实现模块 + 类继承 组合调用

  attr_accessor :module_input

  def module_cmd_exec
    # 漏洞点：模块方法中执行系统命令
    cmd = @module_input || 'echo module_default'
    result = `#{cmd}`
    result.strip
  end

  def module_cmd_hardcoded
    # 漏洞点：模块方法中硬编码执行系统命令
    result = `echo hardcoded_module_cmd`
    result.strip
  end

  def module_code_exec
    # 漏洞点：模块方法中eval外部参数
    code = @module_input || '2 + 2'
    eval(code)
  end

  def module_code_hardcoded
    # 漏洞点：模块方法中eval硬编码代码
    eval('1000 / 10')
  end

  def module_ssrf
    # 漏洞点：模块方法中发起SSRF请求
    url = @module_input || 'http://127.0.0.1'
    uri = URI.parse(url)
    Net::HTTP.get(uri)
  end

  def module_ssrf_hardcoded
    # 漏洞点：模块方法中硬编码发起SSRF请求
    uri = URI.parse('http://127.0.0.1:3000/internal')
    Net::HTTP.get(uri)
  end
end
