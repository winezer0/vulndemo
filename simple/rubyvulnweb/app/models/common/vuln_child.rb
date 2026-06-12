require_relative 'vuln_parent'

class VulnChild < VulnParent
  # 独立子类，继承父类所有漏洞方法
  # 实现子类 → 父类漏洞方法的调用链

  # 覆写父类方法，调用父类漏洞方法
  def execute_from_parent
    # 漏洞点：子类调用父类的命令执行漏洞方法
    vuln_cmd_exec
  end

  def execute_code_from_parent
    # 漏洞点：子类调用父类的代码执行漏洞方法
    vuln_code_exec
  end

  def execute_ssrf_from_parent
    # 漏洞点：子类调用父类的SSRF漏洞方法
    vuln_ssrf
  end

  def execute_hardcoded_from_parent
    # 漏洞点：子类调用父类的硬编码漏洞方法
    vuln_cmd_hardcoded
  end
end
