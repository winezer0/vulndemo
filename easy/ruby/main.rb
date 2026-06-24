require_relative 'func_call'

cmd = ARGV[0] || "calc"
# FuncCall.run_by_child(cmd)
FuncCall.run_by_interface(cmd)
