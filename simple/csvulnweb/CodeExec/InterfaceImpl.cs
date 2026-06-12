using csvulnweb.Common;
using csvulnweb.Interfaces;

namespace csvulnweb.CodeExec
{
    /// <summary>
    /// 代码执行漏洞 - 接口实现类测试
    /// 同时实现 IVulnInterface 接口并继承 VulnParent 类
    /// 调用链：请求参数 → 本类 → VulnParent.VulnerableCodeExec → 漏洞触发点
    /// </summary>
    public class CodeExecInterfaceImpl : VulnParent, IVulnInterface
    {
        /// <summary>
        /// 接口方法实现 - 代码执行中调用命令执行（混合调用）
        /// </summary>
        public string ExecuteCommand(string userInput)
        {
            return base.VulnerableCodeExec(userInput);
        }

        /// <summary>
        /// 接口方法实现 - 代码执行
        /// 调用链：外部参数 → ExecuteCode → VulnerableCodeExec(父类)
        /// </summary>
        public string ExecuteCode(string userInput)
        {
            return base.VulnerableCodeExec(userInput);
        }

        /// <summary>
        /// 接口方法实现 - SSRF（通过代码执行方式构造请求）
        /// </summary>
        public async Task<string> ExecuteSSRF(string targetUrl)
        {
            return base.VulnerableCodeExec($"new System.Net.WebClient().DownloadString(\"{targetUrl}\")");
        }

        /// <summary>
        /// 接口方法实现 - 硬编码代码执行
        /// </summary>
        public string HardcodedCommand()
        {
            return base.HardcodedCmdExec();
        }

        /// <summary>
        /// 接口方法实现 - 硬编码代码执行
        /// </summary>
        public string HardcodedCode()
        {
            return base.HardcodedCodeExec();
        }

        /// <summary>
        /// 接口方法实现 - 硬编码SSRF
        /// </summary>
        public async Task<string> HardcodedSSRFRequest()
        {
            return await base.HardcodedSSRF();
        }
    }
}
