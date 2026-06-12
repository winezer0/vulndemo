require 'net/http'
require 'uri'

class VulnParent
  # 公共父类，定义各类漏洞的基础方法
  # 通过子类继承、模块混入形成多层调用链

  attr_accessor :input

  def initialize(input = nil)
    @input = input
  end

  # 命令执行漏洞方法（外部参数场景）
  def vuln_cmd_exec
    # 漏洞点：父类方法中执行系统命令，子类通过继承调用
    cmd = @input || 'echo parent_default'
    result = `#{cmd}`
    result.strip
  end

  # 命令执行漏洞方法（硬编码场景）
  def vuln_cmd_hardcoded
    # 漏洞点：父类方法中硬编码执行系统命令
    result = `echo hardcoded_parent_cmd`
    result.strip
  end

  # 代码执行漏洞方法（外部参数场景）
  def vuln_code_exec
    # 漏洞点：父类方法中eval外部参数
    code = @input || '1 + 1'
    eval(code)
  end

  # 代码执行漏洞方法（硬编码场景）
  def vuln_code_hardcoded
    # 漏洞点：父类方法中eval硬编码代码
    eval('100 * 100')
  end

  # SSRF漏洞方法（外部参数场景）
  def vuln_ssrf
    # 漏洞点：父类方法中发起SSRF请求
    url = @input || 'http://127.0.0.1'
    uri = URI.parse(url)
    Net::HTTP.get(uri)
  end

  # SSRF漏洞方法（硬编码场景）
  def vuln_ssrf_hardcoded
    # 漏洞点：父类方法中硬编码发起SSRF请求
    uri = URI.parse('http://127.0.0.1:8080/internal')
    Net::HTTP.get(uri)
  end
end
