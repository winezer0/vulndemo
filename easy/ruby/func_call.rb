require_relative 'common/vuln_child'
require_relative 'cmd_exec/interface_impl'

module FuncCall
  def self.run_by_child(cmd)
    child = VulnChild.new
    child.exec(cmd)
  end

  def self.run_by_interface(cmd)
    runner = InterfaceImpl.new
    runner.run(cmd)
  end
end
