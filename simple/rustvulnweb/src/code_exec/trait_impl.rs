use crate::trait_def::vuln_trait::VulnBehavior;
use std::process::Command;

// 实现VulnBehavior trait的结构体
pub struct CodeExecImpl;

impl VulnBehavior for CodeExecImpl {
    fn cmd_exec(&self, _cmd: &str) -> String {
        "命令执行未实现".to_string()
    }

    fn code_exec(&self, code: &str) -> String {
        // 漏洞点：代码执行（模拟）
        let output = Command::new("sh")
            .arg("-c")
            .arg(code)
            .output()
            .expect("执行代码失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    fn ssrf(&self, _url: &str) -> String {
        "SSRF未实现".to_string()
    }
}

// 测试函数
pub fn test_code_exec_trait(code: &str) -> String {
    let impl_struct = CodeExecImpl;
    let result = impl_struct.code_exec(code);
    format!("代码执行(Trait实现)测试完成\n结果: {}", result)
}

// 组合测试
pub fn test_combined(code: &str) -> String {
    use crate::common::vuln_child::VulnChild;
    
    let child = VulnChild::new(20);
    let impl_struct = CodeExecImpl;
    
    let child_result = child.call_parent_code_exec_hardcoded();
    let trait_result = impl_struct.code_exec(code);
    
    format!("组合测试完成\n子结构体结果: {}\nTrait结果: {}", child_result, trait_result)
}
