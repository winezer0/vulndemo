require_relative '../concerns/vuln_module'

class CodeExecModuleImpl
  # 代码执行模块包含测试
  # 调用链：外部参数 → 模块包含类 → VulnModule → eval

  include VulnModule

  def initialize(code = nil)
    @module_input = code
  end

  def module_execute
    # 漏洞点：通过模块混入调用代码执行方法
    module_code_exec
  end

  def module_hardcoded_execute
    # 漏洞点：通过模块混入调用硬编码代码执行方法
    module_code_hardcoded
  end
end
