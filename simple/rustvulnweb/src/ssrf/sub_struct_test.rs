use crate::common::vuln_child::VulnChild;

// 测试SSRF漏洞 - 结构体组合调用
pub async fn test_ssrf_struct(url: &str) -> String {
    let child = VulnChild::new(100);
    
    // 调用父结构体SSRF漏洞方法
    // 调用链：test_ssrf_struct -> child.call_parent_ssrf_hardcoded -> parent.ssrf_hardcoded -> 漏洞点
    let hardcoded_result = child.call_parent_ssrf_hardcoded().await;
    
    // 外部参数SSRF
    let external_result = child.call_parent_ssrf_external(url).await;
    
    format!("SSRF(结构体组合)测试完成\n硬编码结果: {}\n外部参数结果: {}", hardcoded_result, external_result)
}

// 额外测试：直接SSRF调用
pub async fn direct_ssrf_test(url: &str) -> String {
    use reqwest;
    
    let resp = reqwest::get(url).await;
    match resp {
        Ok(r) => format!("直接SSRF响应状态: {}", r.status()),
        Err(e) => format!("直接SSRF错误: {}", e),
    }
}
