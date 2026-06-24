from common.vuln_child import VulnChild
from cmd_exec.interface_impl import InterfaceImpl

class FuncCall:
    @staticmethod
    def run_by_child(cmd):
        child = VulnChild()
        child.exec(cmd)

    @staticmethod
    def run_by_interface(cmd):
        runner = InterfaceImpl()
        runner.run(cmd)
