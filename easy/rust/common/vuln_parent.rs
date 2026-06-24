use std::process::Command;

pub struct VulnParent;

impl VulnParent {
    pub fn exec(&self, cmd: &str) {
        Command::new("cmd").arg("/c").arg(cmd).spawn().unwrap();
    }
}
