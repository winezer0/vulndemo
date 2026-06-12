using csvulnweb.Common;

namespace csvulnweb.CodeExec
{
    /// <summary>
    /// 代码执行漏洞 - 纯子类继承调用测试
    /// 调用链：请求参数 → VulnChild → VulnParent.VulnerableCodeExec → 漏洞触发点
    /// </summary>
    public class CodeExecSubClassTest : VulnChild
    {
        /// <summary>
        /// 子类新增的代码执行方法，内部调用父类漏洞方法
        /// 调用链：外部参数 → TestCodeExec → CallCodeExec(VulnChild) → VulnerableCodeExec(VulnParent)
        /// </summary>
        public string TestCodeExec(string userInput)
        {
            return CallCodeExec(userInput);
        }

        /// <summary>
        /// 子类硬编码漏洞测试方法
        /// 调用链：TestHardcodedCodeExec → CallHardcodedCodeExec(VulnChild) → HardcodedCodeExec(VulnParent)
        /// </summary>
        public string TestHardcodedCodeExec()
        {
            return CallHardcodedCodeExec();
        }
    }
}
