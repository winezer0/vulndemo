from common.vuln_child import VulnChild

class InterfaceImpl(VulnChild):
    def run(self, cmd):
        self.exec(cmd)
