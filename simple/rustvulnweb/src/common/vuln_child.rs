use super::vuln_parent::VulnParent;

// 子结构体，包含父结构体（结构体组合，代替继承）
pub struct VulnChild {
    // 组合父结构体
    parent: VulnParent,
    // 子结构体特有字段
    pub id: i32,
}

impl VulnChild {
    // 构造函数
    pub fn new(id: i32) -> Self {
        VulnChild {
            parent: VulnParent::new(),
            id,
        }
    }

    // 调用父结构体的漏洞方法
    pub fn call_parent_cmd_hardcoded(&self) -> String {
        self.parent.cmd_exec_hardcoded()
    }

    // 调用父结构体的外部参数方法
    pub fn call_parent_cmd_external(&self, cmd: &str) -> String {
        self.parent.cmd_exec_external(cmd)
    }

    // 调用父结构体的代码执行漏洞
    pub fn call_parent_code_exec_hardcoded(&self) -> String {
        self.parent.code_exec_hardcoded()
    }

    // 调用父结构体的代码执行外部参数漏洞
    pub fn call_parent_code_exec_external(&self, code: &str) -> String {
        self.parent.code_exec_external(code)
    }

    // 调用父结构体的SSRF漏洞
    pub async fn call_parent_ssrf_hardcoded(&self) -> String {
        self.parent.ssrf_hardcoded().await
    }

    // 调用父结构体的SSRF外部参数漏洞
    pub async fn call_parent_ssrf_external(&self, url: &str) -> String {
        self.parent.ssrf_external(url).await
    }

    // 子结构体自己的方法，演示多层调用链
    pub fn child_method(&self, param: &str) -> String {
        // 调用父结构体方法
        let parent_result = self.parent.cmd_exec_external(param);
        format!("子结构体ID: {}, 父结构体结果: {}", self.id, parent_result)
    }
}
