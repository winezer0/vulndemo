require_relative '../concerns/vuln_module'

class CmdExecModuleImpl
  # 命令执行模块包含测试
  # 调用链：外部参数 → 模块包含类 → VulnModule → 系统命令

  include VulnModule

  def initialize(cmd = nil)
    @module_input = cmd
  end

  def module_execute
    # 漏洞点：通过模块混入调用命令执行方法
    module_cmd_exec
  end

  def module_hardcoded_execute
    # 漏洞点：通过模块混入调用硬编码命令执行方法
    module_cmd_hardcoded
  end
end
