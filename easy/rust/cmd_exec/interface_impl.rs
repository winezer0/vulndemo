use crate::common::vuln_child::VulnChild;
use crate::iface::vuln_runner::VulnRunner;

pub struct InterfaceImpl {
    pub child: VulnChild,
}

impl VulnRunner for InterfaceImpl {
    fn run(&self, cmd: &str) {
        self.child.parent.exec(cmd);
    }
}
