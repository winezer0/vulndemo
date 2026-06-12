use crate::common::vuln_child::VulnChild;

// 测试命令执行漏洞 - 结构体组合调用
pub fn test_cmd_exec_struct(cmd: &str) -> String {
    // 创建子结构体实例
    let child = VulnChild::new(1);
    
    // 调用子结构体方法，内部调用父结构体漏洞方法
    // 调用链：test_cmd_exec_struct -> child.call_parent_cmd_external -> parent.cmd_exec_external -> 漏洞点
    let result = child.call_parent_cmd_external(cmd);
    
    // 同时测试硬编码漏洞
    let hardcoded_result = child.call_parent_cmd_hardcoded();
    
    format!("结构体组合测试完成\n硬编码结果: {}\n外部参数结果: {}", hardcoded_result, result)
}

// 额外测试：子结构体自身方法
pub fn test_child_method(cmd: &str) -> String {
    let child = VulnChild::new(2);
    child.child_method(cmd)
}
