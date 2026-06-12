Rails.application.routes.draw do
  root 'application#index'

  # 命令执行测试路由
  get  '/cmd_exec/exec',        to: 'cmd_exec#exec_command'
  post '/cmd_exec/exec',        to: 'cmd_exec#exec_command'
  get  '/cmd_exec/child',       to: 'cmd_exec#child_exec'
  get  '/cmd_exec/module',      to: 'cmd_exec#module_exec'
  get  '/cmd_exec/hardcoded',   to: 'cmd_exec#hardcoded_cmd'

  # 代码执行测试路由
  get  '/code_exec/exec',       to: 'code_exec#exec_code'
  post '/code_exec/exec',       to: 'code_exec#exec_code'
  get  '/code_exec/child',      to: 'code_exec#child_exec'
  get  '/code_exec/module',     to: 'code_exec#module_exec'
  get  '/code_exec/hardcoded',  to: 'code_exec#hardcoded_code'

  # SSRF测试路由
  get  '/ssrf/fetch',           to: 'ssrf#fetch_url'
  post '/ssrf/fetch',           to: 'ssrf#fetch_url'
  get  '/ssrf/child',           to: 'ssrf#child_fetch'
  get  '/ssrf/module',          to: 'ssrf#module_fetch'
  get  '/ssrf/hardcoded',       to: 'ssrf#hardcoded_ssrf'

  # 跨文件函数调用路由
  get  '/func_call/cross',  to: 'func_call#cross_file_call'
  post '/func_call/cross',  to: 'func_call#cross_file_call'
end
