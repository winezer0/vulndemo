// 定义漏洞行为的trait，代替接口
pub trait VulnBehavior {
    // 命令执行漏洞
    fn cmd_exec(&self, cmd: &str) -> String;
    
    // 代码执行漏洞（模拟）
    fn code_exec(&self, code: &str) -> String;
    
    // SSRF漏洞（同步版本，使用reqwest::blocking）
    fn ssrf(&self, url: &str) -> String;
}

// 定义硬编码漏洞的trait
pub trait VulnHardcoded {
    fn cmd_exec_hardcoded(&self) -> String;
    fn code_exec_hardcoded(&self) -> String;
    fn ssrf_hardcoded(&self) -> String;
}
