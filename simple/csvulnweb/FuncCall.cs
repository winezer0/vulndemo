using csvulnweb.Common;

namespace csvulnweb
{
    /// <summary>
    /// 通用跨文件函数调用、硬编码漏洞测试
    /// 本文件演示跨文件的函数调用和类实例化调用
    /// </summary>
    public static class FuncCall
    {
        /// <summary>
        /// 跨文件调用 - 直接实例化VulnParent并调用漏洞方法
        /// 调用链：外部参数 → 本静态方法 → VulnParent.VulnerableCmdExecWindows
        /// </summary>
        public static string CrossFileCallCmdExec(string userInput)
        {
            var parent = new VulnParent(); // 跨文件实例化
            return parent.VulnerableCmdExecWindows(userInput); // 跨文件调用漏洞方法
        }

        /// <summary>
        /// 跨文件调用 - 直接实例化VulnParent并调用代码执行漏洞方法
        /// </summary>
        public static string CrossFileCallCodeExec(string userInput)
        {
            var parent = new VulnParent();
            return parent.VulnerableCodeExec(userInput);
        }

        /// <summary>
        /// 跨文件调用 - 直接实例化VulnParent并调用SSRF漏洞方法
        /// </summary>
        public static async Task<string> CrossFileCallSSRF(string targetUrl)
        {
            var parent = new VulnParent();
            return await parent.VulnerableSSRF(targetUrl);
        }

        /// <summary>
        /// 跨文件调用 - 实例化子类VulnChild并调用
        /// 调用链：外部参数 → 本方法 → VulnChild → VulnParent
        /// </summary>
        public static string CrossFileCallViaChild(string userInput)
        {
            var child = new VulnChild();
            return child.CallCmdExec(userInput);
        }

        /// <summary>
        /// 硬编码漏洞测试 - 跨文件静态调用父类硬编码方法
        /// </summary>
        public static string HardcodedCrossFileCmdExec()
        {
            var parent = new VulnParent();
            return parent.HardcodedCmdExec();
        }

        /// <summary>
        /// 硬编码漏洞测试 - 跨文件静态调用父类硬编码代码执行
        /// </summary>
        public static string HardcodedCrossFileCodeExec()
        {
            var parent = new VulnParent();
            return parent.HardcodedCodeExec();
        }

        /// <summary>
        /// 硬编码漏洞测试 - 跨文件静态调用父类硬编码SSRF
        /// </summary>
        public static async Task<string> HardcodedCrossFileSSRF()
        {
            var parent = new VulnParent();
            return await parent.HardcodedSSRF();
        }
    }
}
