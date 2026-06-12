use std::process::Command;
use reqwest;

// 父结构体，包含所有漏洞核心方法
pub struct VulnParent {
    // 公共字段
    pub name: String,
}

impl VulnParent {
    // 构造函数
    pub fn new() -> Self {
        VulnParent {
            name: "VulnParent".to_string(),
        }
    }

    // 命令执行漏洞 - 硬编码
    pub fn cmd_exec_hardcoded(&self) -> String {
        let output = Command::new("echo")
            .arg("hardcoded command from VulnParent")
            .output()
            .expect("执行命令失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    // 命令执行漏洞 - 外部参数
    pub fn cmd_exec_external(&self, cmd: &str) -> String {
        // 漏洞点：直接使用外部参数构造命令
        let output = Command::new("sh")
            .arg("-c")
            .arg(cmd)
            .output()
            .expect("执行命令失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    // 代码执行漏洞 - 模拟（Rust没有eval，使用echo命令模拟代码执行）
    pub fn code_exec_hardcoded(&self) -> String {
        // 漏洞点：将字符串作为命令执行（模拟代码执行）
        let output = Command::new("sh")
            .arg("-c")
            .arg("echo 'hardcoded code executed'")
            .output()
            .expect("执行代码失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    // 代码执行漏洞 - 外部参数
    pub fn code_exec_external(&self, code: &str) -> String {
        // 漏洞点：将外部代码作为命令执行
        let output = Command::new("sh")
            .arg("-c")
            .arg(code)
            .output()
            .expect("执行代码失败");
        String::from_utf8_lossy(&output.stdout).to_string()
    }

    // SSRF漏洞 - 硬编码
    pub async fn ssrf_hardcoded(&self) -> String {
        let url = "http://127.0.0.1:8080";
        let resp = reqwest::get(url).await;
        match resp {
            Ok(r) => format!("SSRF硬编码响应状态: {}", r.status()),
            Err(e) => format!("SSRF硬编码错误: {}", e),
        }
    }

    // SSRF漏洞 - 外部参数
    pub async fn ssrf_external(&self, url: &str) -> String {
        // 漏洞点：直接请求外部URL
        let resp = reqwest::get(url).await;
        match resp {
            Ok(r) => format!("SSRF外部参数响应状态: {}", r.status()),
            Err(e) => format!("SSRF外部参数错误: {}", e),
        }
    }
}
