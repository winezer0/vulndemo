require_relative '../common/vuln_child'

class CmdExecSubClass < VulnChild
  # 命令执行子类继承测试
  # 调用链：外部参数 → 子类 → 父类VulnParent → 系统命令

  def initialize(cmd = nil)
    super(cmd)
  end

  def execute_from_parent
    # 漏洞点：继承调用父类命令执行方法
    vuln_cmd_exec
  end

  def execute_hardcoded
    # 漏洞点：继承调用父类硬编码命令执行方法
    vuln_cmd_hardcoded
  end
end
