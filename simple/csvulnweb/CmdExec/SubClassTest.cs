using csvulnweb.Common;

namespace csvulnweb.CmdExec
{
    /// <summary>
    /// 命令执行漏洞 - 纯子类继承调用测试
    /// 调用链：请求参数 → VulnChild → VulnParent.VulnerableCmdExecWindows → 漏洞触发点
    /// </summary>
    public class CmdExecSubClassTest : VulnChild
    {
        /// <summary>
        /// 子类新增的命令执行方法，内部调用父类漏洞方法
        /// 调用链：外部参数 → TestCmdExec → CallCmdExec(VulnChild) → VulnerableCmdExecWindows(VulnParent)
        /// </summary>
        public string TestCmdExec(string userInput)
        {
            return CallCmdExec(userInput);
        }

        /// <summary>
        /// 子类硬编码漏洞测试方法
        /// 调用链：TestHardcodedCmdExec → CallHardcodedCmdExec(VulnChild) → HardcodedCmdExec(VulnParent)
        /// </summary>
        public string TestHardcodedCmdExec()
        {
            return CallHardcodedCmdExec();
        }
    }
}
