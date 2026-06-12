const { execSync } = require('child_process');
const axios = require('axios');

class VulnParent {
  /**
   * 命令执行漏洞 - 外部可控数据源
   * @param {string} input - 来自req.query的用户输入
   */
  cmdExecExternal(input) {
    // 漏洞点: 命令执行 - 用户输入直接拼接到命令字符串
    const result = execSync(`echo ${input}`, { encoding: 'utf-8' });
    return result;
  }

  /**
   * 命令执行漏洞 - 硬编码数据源
   */
  cmdExecHardcoded() {
    // 漏洞点: 命令执行 - 硬编码参数拼接到命令
    const result = execSync('echo hardcoded_test_data_123', { encoding: 'utf-8' });
    return result;
  }

  /**
   * 代码执行漏洞 - 外部可控数据源
   * @param {string} input - 来自req.query的用户输入
   */
  codeExecExternal(input) {
    // 漏洞点: 代码执行 - 用户输入直接传入eval
    const result = eval(input);
    return result;
  }

  /**
   * 代码执行漏洞 - 硬编码数据源
   */
  codeExecHardcoded() {
    // 漏洞点: 代码执行 - 硬编码字符串传入eval
    const result = eval('"hardcoded_eval_result_" + 42');
    return result;
  }

  /**
   * SSRF漏洞 - 外部可控数据源
   * @param {string} url - 来自req.query的用户输入
   */
  async ssrfExternal(url) {
    // 漏洞点: SSRF - 用户输入的URL直接发起请求
    const response = await axios.get(url);
    return response.data;
  }

  /**
   * SSRF漏洞 - 硬编码数据源
   */
  async ssrfHardcoded() {
    // 漏洞点: SSRF - 硬编码URL发起请求
    const response = await axios.get('http://127.0.0.1:8080/internal');
    return response.data;
  }
}

module.exports = VulnParent;
