require_relative '../models/common/vuln_parent'
require_relative '../models/common/vuln_child'
require_relative '../models/code_exec/sub_class_test'
require_relative '../models/code_exec/module_impl'

class CodeExecController < ApplicationController
  # 代码执行测试控制器

  def exec_code
    code = params[:code] || '1 + 1'
    # 漏洞点：直接eval外部参数
    result = eval(code)
    render plain: "代码执行结果: #{result}"
  end

  def child_exec
    code = params[:code] || '1 + 2'
    # 通过子类调用父类漏洞方法，形成 外部参数 → 子类 → 父类 → eval 调用链
    child = CodeExecSubClass.new(code)
    result = child.execute_from_parent
    render plain: "子类继承调用结果: #{result}"
  end

  def module_exec
    code = params[:code] || '1 + 3'
    # 通过模块包含类调用漏洞方法
    impl = CodeExecModuleImpl.new(code)
    result = impl.module_execute
    render plain: "模块混入调用结果: #{result}"
  end

  def hardcoded_code
    parent = VulnParent.new
    result = parent.vuln_code_hardcoded
    render plain: "硬编码代码执行结果: #{result}"
  end
end
