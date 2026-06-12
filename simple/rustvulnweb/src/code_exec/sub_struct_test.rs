use crate::common::vuln_child::VulnChild;

// 测试代码执行漏洞 - 结构体组合调用
pub fn test_code_exec_struct(code: &str) -> String {
    let child = VulnChild::new(10);
    
    // 调用父结构体代码执行漏洞方法
    // 调用链：test_code_exec_struct -> child.call_parent_code_exec_hardcoded -> parent.code_exec_hardcoded -> 漏洞点
    let hardcoded_result = child.call_parent_code_exec_hardcoded();
    
    // 外部参数代码执行
    // 调用链：test_code_exec_struct -> child.call_parent_code_exec_external -> parent.code_exec_external -> 漏洞点
    let external_result = child.call_parent_code_exec_external(code);
    
    format!("代码执行(结构体组合)测试完成\n硬编码结果: {}\n外部参数结果: {}", hardcoded_result, external_result)
}

// 额外测试：模拟动态代码执行
pub fn simulate_dynamic_code_exec(code: &str) -> String {
    // 漏洞点：将字符串作为代码执行（模拟）
    use std::process::Command;
    
    let output = Command::new("sh")
        .arg("-c")
        .arg(format!("echo '执行代码: {}'", code))
        .output()
        .expect("执行代码失败");
    
    String::from_utf8_lossy(&output.stdout).to_string()
}
