require_relative '../common/vuln_child'

class CodeExecSubClass < VulnChild
  # 代码执行子类继承测试
  # 调用链：外部参数 → 子类 → 父类VulnParent → eval

  def initialize(code = nil)
    super(code)
  end

  def execute_from_parent
    # 漏洞点：继承调用父类代码执行方法
    vuln_code_exec
  end

  def execute_hardcoded
    # 漏洞点：继承调用父类硬编码代码执行方法
    vuln_code_hardcoded
  end
end
