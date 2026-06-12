use crate::trait_def::vuln_trait::VulnBehavior;
use std::process::Command;

// 实现VulnBehavior trait的结构体
pub struct CmdExecImpl;

impl VulnBehavior for CmdExecImpl {
    fn cmd_exec(&self, cmd: &str) -> String {
        // 漏洞点：命令执行
        let output = Command::new("sh")
            .arg("-c")
            .arg(cmd)
            .output()
            .expect("执行命令失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    fn code_exec(&self, _code: &str) -> String {
        // 此trait方法未实现，返回提示
        "代码执行未实现".to_string()
    }

    fn ssrf(&self, _url: &str) -> String {
        "SSRF未实现".to_string()
    }
}

// 测试函数
pub fn test_cmd_exec_trait(cmd: &str) -> String {
    let impl_struct = CmdExecImpl;
    // 调用trait方法
    // 调用链：test_cmd_exec_trait -> impl_struct.cmd_exec -> 漏洞点
    let result = impl_struct.cmd_exec(cmd);
    format!("Trait实现测试完成\n结果: {}", result)
}

// 组合使用：trait + 结构体组合
pub fn test_combined(cmd: &str) -> String {
    use crate::common::vuln_child::VulnChild;
    
    let child = VulnChild::new(3);
    let impl_struct = CmdExecImpl;
    
    // 调用子结构体方法
    let child_result = child.call_parent_cmd_external(cmd);
    // 调用trait方法
    let trait_result = impl_struct.cmd_exec(cmd);
    
    format!("组合测试完成\n子结构体结果: {}\nTrait结果: {}", child_result, trait_result)
}
