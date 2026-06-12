namespace csvulnweb.Common
{
    /// <summary>
    /// 独立子类，继承 VulnParent
    /// 调用链：外部请求参数 → 本类方法 → 父类漏洞方法 → 漏洞触发点
    /// </summary>
    public class VulnChild : VulnParent
    {
        /// <summary>
        /// 子类封装命令执行 - 接收外部参数后调用父类漏洞方法
        /// 调用链：请求参数 → 本方法 → base.VulnerableCmdExecWindows()
        /// </summary>
        public string CallCmdExec(string userInput)
        {
            return base.VulnerableCmdExecWindows(userInput); // 调用父类漏洞方法
        }

        /// <summary>
        /// 子类封装代码执行 - 接收外部参数后调用父类漏洞方法
        /// 调用链：请求参数 → 本方法 → base.VulnerableCodeExec()
        /// </summary>
        public string CallCodeExec(string userInput)
        {
            return base.VulnerableCodeExec(userInput); // 调用父类漏洞方法
        }

        /// <summary>
        /// 子类封装SSRF - 接收外部参数后调用父类漏洞方法
        /// 调用链：请求参数 → 本方法 → base.VulnerableSSRF()
        /// </summary>
        public async Task<string> CallSSRF(string targetUrl)
        {
            return await base.VulnerableSSRF(targetUrl); // 调用父类漏洞方法
        }

        /// <summary>
        /// 子类硬编码漏洞调用
        /// </summary>
        public string CallHardcodedCmdExec()
        {
            return base.HardcodedCmdExec();
        }

        public string CallHardcodedCodeExec()
        {
            return base.HardcodedCodeExec();
        }

        public async Task<string> CallHardcodedSSRF()
        {
            return await base.HardcodedSSRF();
        }
    }
}
