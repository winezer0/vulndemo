using System.Diagnostics;
using System.Text;
using Microsoft.CodeAnalysis.CSharp.Scripting;
using Microsoft.CodeAnalysis.Scripting;

namespace csvulnweb.Common
{
    /// <summary>
    /// 公共父类，集中存放所有漏洞核心方法
    /// 调用链：外部请求参数 → 子类/接口实现类 → 本类漏洞方法 → 漏洞触发点
    /// </summary>
    public class VulnParent
    {
        /// <summary>
        /// 命令执行漏洞 - 通过Process直接拼接用户输入执行系统命令
        /// 漏洞触发点：Process.Start 拼接外部参数
        /// </summary>
        public string VulnerableCmdExec(string userInput)
        {
            var process = new Process();
            process.StartInfo.FileName = "/bin/sh"; // Linux环境
            process.StartInfo.Arguments = $"-c \"{userInput}\""; // 漏洞点：用户输入直接拼接到命令参数
            process.StartInfo.RedirectStandardOutput = true;
            process.StartInfo.RedirectStandardError = true;
            process.StartInfo.UseShellExecute = false;
            process.Start();
            string output = process.StandardOutput.ReadToEnd();
            process.WaitForExit();
            return output;
        }

        /// <summary>
        /// 命令执行漏洞（Windows环境）- 通过cmd.exe执行
        /// 漏洞触发点：Process.Start 拼接外部参数
        /// </summary>
        public string VulnerableCmdExecWindows(string userInput)
        {
            var process = new Process();
            process.StartInfo.FileName = "cmd.exe";
            process.StartInfo.Arguments = $"/c {userInput}"; // 漏洞点：用户输入直接拼接到cmd命令
            process.StartInfo.RedirectStandardOutput = true;
            process.StartInfo.RedirectStandardError = true;
            process.StartInfo.UseShellExecute = false;
            process.Start();
            string output = process.StandardOutput.ReadToEnd();
            process.WaitForExit();
            return output;
        }

        /// <summary>
        /// 代码执行漏洞 - 通过Roslyn动态编译并执行C#表达式
        /// 漏洞触发点：CSharpScript.EvaluateAsync 执行外部可控表达式
        /// </summary>
        public string VulnerableCodeExec(string userInput)
        {
            try
            {
                var options = ScriptOptions.Default;
                options = options.AddReferences(typeof(object).Assembly);
                var result = CSharpScript.EvaluateAsync(userInput, options).Result; // 漏洞点：用户输入直接作为代码执行
                return result?.ToString() ?? "null";
            }
            catch (Exception ex)
            {
                return $"Error: {ex.Message}";
            }
        }

        /// <summary>
        /// 代码执行漏洞 - 通过Process启动dotnet脚本执行
        /// 漏洞触发点：动态生成csx文件并执行
        /// </summary>
        public string VulnerableCodeExecViaProcess(string userInput)
        {
            string tempFile = Path.GetTempFileName() + ".csx";
            try
            {
                File.WriteAllText(tempFile, userInput); // 漏洞点：用户输入写入临时脚本文件
                var process = new Process();
                process.StartInfo.FileName = "dotnet-script"; // 需要安装 dotnet-script 工具
                process.StartInfo.Arguments = tempFile;
                process.StartInfo.RedirectStandardOutput = true;
                process.StartInfo.UseShellExecute = false;
                process.Start();
                string output = process.StandardOutput.ReadToEnd();
                process.WaitForExit();
                return output;
            }
            finally
            {
                if (File.Exists(tempFile))
                    File.Delete(tempFile);
            }
        }

        /// <summary>
        /// SSRF漏洞 - 通过HttpClient请求用户指定的URL
        /// 漏洞触发点：HttpClient.GetAsync 直接请求外部可控地址
        /// </summary>
        public async Task<string> VulnerableSSRF(string targetUrl)
        {
            try
            {
                using var httpClient = new HttpClient();
                httpClient.Timeout = TimeSpan.FromSeconds(10);
                var response = await httpClient.GetAsync(targetUrl); // 漏洞点：用户指定URL直接请求
                return await response.Content.ReadAsStringAsync();
            }
            catch (Exception ex)
            {
                return $"SSRF Error: {ex.Message}";
            }
        }

        /// <summary>
        /// SSRF漏洞 - 带自定义请求头的SSRF
        /// 漏洞触发点：HttpClient 同时拼接自定义Header和目标URL
        /// </summary>
        public async Task<string> VulnerableSSRFWithHeaders(string targetUrl, string customHeader)
        {
            try
            {
                using var httpClient = new HttpClient();
                httpClient.DefaultRequestHeaders.Add("X-Custom-Header", customHeader); // 漏洞点：用户可控Header
                var response = await httpClient.GetAsync(targetUrl); // 漏洞点：用户指定URL直接请求
                return await response.Content.ReadAsStringAsync();
            }
            catch (Exception ex)
            {
                return $"SSRF Error: {ex.Message}";
            }
        }

        /// <summary>
        /// 硬编码命令执行漏洞测试 - 无外部参数，内置固定payload
        /// 用于验证工具对硬编码漏洞的检测能力
        /// </summary>
        public string HardcodedCmdExec()
        {
            string cmd = "whoami"; // 硬编码的危险命令
            var process = new Process();
            process.StartInfo.FileName = "cmd.exe";
            process.StartInfo.Arguments = $"/c {cmd}"; // 硬编码漏洞点
            process.StartInfo.RedirectStandardOutput = true;
            process.StartInfo.UseShellExecute = false;
            process.Start();
            string output = process.StandardOutput.ReadToEnd();
            process.WaitForExit();
            return output;
        }

        /// <summary>
        /// 硬编码代码执行漏洞测试 - 无外部参数，内置固定表达式
        /// </summary>
        public string HardcodedCodeExec()
        {
            string code = "System.Environment.GetFolderPath(System.Environment.SpecialFolder.UserProfile)"; // 硬编码危险表达式
            var options = ScriptOptions.Default;
            var result = CSharpScript.EvaluateAsync(code, options).Result; // 硬编码漏洞点
            return result?.ToString() ?? "null";
        }

        /// <summary>
        /// 硬编码SSRF漏洞测试 - 无外部参数，内置固定URL
        /// </summary>
        public async Task<string> HardcodedSSRF()
        {
            string url = "http://169.254.169.254/latest/meta-data/"; // 硬编码敏感内网地址（云环境元数据）
            using var httpClient = new HttpClient();
            httpClient.Timeout = TimeSpan.FromSeconds(5);
            var response = await httpClient.GetAsync(url); // 硬编码SSRF漏洞点
            return await response.Content.ReadAsStringAsync();
        }
    }
}
