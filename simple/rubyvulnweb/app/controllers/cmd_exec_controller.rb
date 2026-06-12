require_relative '../models/common/vuln_parent'
require_relative '../models/common/vuln_child'
require_relative '../models/cmd_exec/sub_class_test'
require_relative '../models/cmd_exec/module_impl'

class CmdExecController < ApplicationController
  # 命令执行测试控制器

  def exec_command
    cmd = params[:cmd] || 'echo hello'
    # 漏洞点：直接使用外部参数拼接系统命令
    result = `#{cmd}`
    render plain: "命令执行结果: #{result}"
  end

  def child_exec
    cmd = params[:cmd] || 'echo child'
    # 通过子类调用父类漏洞方法，形成 外部参数 → 子类 → 父类 → 系统命令 调用链
    child = CmdExecSubClass.new(cmd)
    result = child.execute_from_parent
    render plain: "子类继承调用结果: #{result}"
  end

  def module_exec
    cmd = params[:cmd] || 'echo module'
    # 通过模块包含类调用漏洞方法
    impl = CmdExecModuleImpl.new(cmd)
    result = impl.module_execute
    render plain: "模块混入调用结果: #{result}"
  end

  def hardcoded_cmd
    parent = VulnParent.new
    result = parent.vuln_cmd_hardcoded
    render plain: "硬编码命令执行结果: #{result}"
  end
end
