using csvulnweb.Common;

namespace csvulnweb.SSRF
{
    /// <summary>
    /// SSRF漏洞 - 纯子类继承调用测试
    /// 调用链：请求参数 → VulnChild → VulnParent.VulnerableSSRF → 漏洞触发点
    /// </summary>
    public class SSRFSubClassTest : VulnChild
    {
        /// <summary>
        /// 子类新增的SSRF方法，内部调用父类漏洞方法
        /// 调用链：外部参数 → TestSSRF → CallSSRF(VulnChild) → VulnerableSSRF(VulnParent)
        /// </summary>
        public async Task<string> TestSSRF(string targetUrl)
        {
            return await CallSSRF(targetUrl);
        }

        /// <summary>
        /// 子类硬编码漏洞测试方法
        /// 调用链：TestHardcodedSSRF → CallHardcodedSSRF(VulnChild) → HardcodedSSRF(VulnParent)
        /// </summary>
        public async Task<string> TestHardcodedSSRF()
        {
            return await CallHardcodedSSRF();
        }
    }
}
