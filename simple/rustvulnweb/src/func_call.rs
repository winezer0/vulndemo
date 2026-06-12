use std::process::Command;
use crate::common::vuln_parent::VulnParent;

// 跨文件函数调用示例，演示硬编码漏洞
pub fn vuln_function(param: &str) -> String {
    // 调用父结构体的漏洞方法
    let parent = VulnParent::new();
    parent.cmd_exec_hardcoded()
}

// 硬编码命令执行漏洞
pub fn hardcoded_cmd_exec() -> String {
    let output = Command::new("echo")
        .arg("hardcoded command executed")
        .output()
        .expect("执行命令失败");
    String::from_utf8_lossy(&output.stdout).to_string()
}

// 跨文件调用父结构体方法
pub fn cross_file_call() -> String {
    let parent = VulnParent::new();
    parent.cmd_exec_external("ls -la")
}
