use crate::common::vuln_child::VulnChild;
use crate::common::vuln_parent::VulnParent;
use crate::cmd_exec::interface_impl::InterfaceImpl;
use crate::iface::vuln_runner::VulnRunner;

pub fn run_by_child(cmd: &str) {
    let child = VulnChild { parent: VulnParent };
    child.parent.exec(cmd);
}

pub fn run_by_interface(cmd: &str) {
    let impl_obj = InterfaceImpl {
        child: VulnChild { parent: VulnParent },
    };
    let runner: &dyn VulnRunner = &impl_obj;
    runner.run(cmd);
}
