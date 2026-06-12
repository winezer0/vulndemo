require_relative '../models/common/vuln_parent'
require_relative '../models/common/vuln_child'
require_relative '../models/cmd_exec/sub_class_test'
require_relative '../models/cmd_exec/module_impl'
require_relative '../models/code_exec/sub_class_test'
require_relative '../models/code_exec/module_impl'
require_relative '../models/ssrf/sub_class_test'
require_relative '../models/ssrf/module_impl'

# 跨文件调用辅助函数
def cross_file_helper_cmd(cmd)
  # 漏洞点：跨文件函数调用链中的系统命令执行
  `#{cmd}`
end

def cross_file_helper_eval(code)
  # 漏洞点：跨文件函数调用链中的代码执行
  eval(code)
end

def cross_file_helper_ssrf(url)
  # 漏洞点：跨文件函数调用链中的SSRF请求
  uri = URI.parse(url)
  Net::HTTP.get(uri)
end

class FuncCallController < ApplicationController
  # 跨文件函数调用测试控制器

  def cross_file_call
    results = []

    # 场景1：外部参数触发跨文件命令执行
    cmd = params[:cmd] || 'echo cross_cmd'
    results << "跨文件命令执行: #{cross_file_helper_cmd(cmd)}"

    # 场景2：外部参数触发跨文件代码执行
    code = params[:code] || '1 + 100'
    results << "跨文件代码执行: #{cross_file_helper_eval(code)}"

    # 场景3：外部参数触发跨文件SSRF
    url = params[:url] || 'http://127.0.0.1'
    results << "跨文件SSRF: #{cross_file_helper_ssrf(url)[0..200]}"

    # 场景4：硬编码跨文件调用
    results << "硬编码跨文件命令: #{cross_file_helper_cmd('echo hardcoded')}"

    render plain: results.join("\n")
  end
end
