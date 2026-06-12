use crate::trait_def::vuln_trait::VulnBehavior;

// 实现VulnBehavior trait的结构体
pub struct SsrfImpl;

impl VulnBehavior for SsrfImpl {
    fn cmd_exec(&self, _cmd: &str) -> String {
        "命令执行未实现".to_string()
    }

    fn code_exec(&self, _code: &str) -> String {
        "代码执行未实现".to_string()
    }

    fn ssrf(&self, url: &str) -> String {
        // 漏洞点：使用reqwest::blocking发起SSRF请求
        match reqwest::blocking::get(url) {
            Ok(resp) => format!("SSRF响应状态: {}, URL: {}", resp.status(), url),
            Err(e) => format!("SSRF错误: {}, URL: {}", e, url),
        }
    }
}

// 测试函数
pub fn test_ssrf_trait(url: &str) -> String {
    let impl_struct = SsrfImpl;
    let result = impl_struct.ssrf(url);
    
    format!("SSRF(Trait实现)测试完成\n结果: {}", result)
}

// 组合测试
pub async fn test_combined(url: &str) -> String {
    use crate::common::vuln_child::VulnChild;
    
    let child = VulnChild::new(200);
    let impl_struct = SsrfImpl;
    
    let child_result = child.call_parent_ssrf_external(url).await;
    let trait_result = impl_struct.ssrf(url);
    
    format!("组合测试完成\n子结构体结果: {}\nTrait结果: {}", child_result, trait_result)
}
