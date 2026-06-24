require_relative '../common/vuln_child'

class InterfaceImpl < VulnChild
  def run(cmd)
    exec(cmd)
  end
end
