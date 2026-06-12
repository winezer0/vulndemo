/**
 * 接口抽象类定义 - IVulnInterface
 * 定义漏洞操作的接口契约
 */
class IVulnInterface {
  /**
   * 命令执行接口方法
   * @param {string} input - 外部输入参数
   */
  doExecCmd(input) {
    throw new Error('Method doExecCmd() must be implemented');
  }

  /**
   * 命令执行接口方法 - 硬编码版本
   */
  doExecCmdHardcoded() {
    throw new Error('Method doExecCmdHardcoded() must be implemented');
  }

  /**
   * 代码执行接口方法
   * @param {string} input - 外部输入参数
   */
  doExecCode(input) {
    throw new Error('Method doExecCode() must be implemented');
  }

  /**
   * 代码执行接口方法 - 硬编码版本
   */
  doExecCodeHardcoded() {
    throw new Error('Method doExecCodeHardcoded() must be implemented');
  }

  /**
   * SSRF接口方法
   * @param {string} url - 外部输入URL
   */
  async doFetchSSRF(url) {
    throw new Error('Method doFetchSSRF() must be implemented');
  }

  /**
   * SSRF接口方法 - 硬编码版本
   */
  async doFetchSSRFHardcoded() {
    throw new Error('Method doFetchSSRFHardcoded() must be implemented');
  }
}

module.exports = IVulnInterface;
