namespace csvulnweb.Interfaces
{
    /// <summary>
    /// 漏洞接口定义
    /// 各漏洞目录的接口实现类需实现本接口的全部方法
    /// 调用链：请求参数 → 接口实现类 → 父类漏洞方法 → 漏洞触发点
    /// </summary>
    public interface IVulnInterface
    {
        /// <summary>
        /// 命令执行接口方法
        /// </summary>
        string ExecuteCommand(string userInput);

        /// <summary>
        /// 代码执行接口方法
        /// </summary>
        string ExecuteCode(string userInput);

        /// <summary>
        /// SSRF接口方法
        /// </summary>
        Task<string> ExecuteSSRF(string targetUrl);

        /// <summary>
        /// 硬编码命令执行接口方法
        /// </summary>
        string HardcodedCommand();

        /// <summary>
        /// 硬编码代码执行接口方法
        /// </summary>
        string HardcodedCode();

        /// <summary>
        /// 硬编码SSRF接口方法
        /// </summary>
        Task<string> HardcodedSSRFRequest();
    }
}
