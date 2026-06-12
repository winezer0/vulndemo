const VulnParent = require('./VulnParent');

class VulnChild extends VulnParent {
  /**
   * 子类封装 - 调用父类cmdExecExternal
   * @param {string} input - 用户输入参数
   */
  execCmd(input) {
    // 调用链: 子类 -> 父类cmdExecExternal -> 漏洞点
    return this.cmdExecExternal(input);
  }

  /**
   * 子类封装 - 调用父类cmdExecHardcoded
   */
  execCmdHardcoded() {
    // 调用链: 子类 -> 父类cmdExecHardcoded -> 漏洞点
    return this.cmdExecHardcoded();
  }

  /**
   * 子类封装 - 调用父类codeExecExternal
   * @param {string} input - 用户输入参数
   */
  execCode(input) {
    // 调用链: 子类 -> 父类codeExecExternal -> 漏洞点
    return this.codeExecExternal(input);
  }

  /**
   * 子类封装 - 调用父类codeExecHardcoded
   */
  execCodeHardcoded() {
    // 调用链: 子类 -> 父类codeExecHardcoded -> 漏洞点
    return this.codeExecHardcoded();
  }

  /**
   * 子类封装 - 调用父类ssrfExternal
   * @param {string} url - 用户输入URL
   */
  async fetchSSRF(url) {
    // 调用链: 子类 -> 父类ssrfExternal -> 漏洞点
    return await this.ssrfExternal(url);
  }

  /**
   * 子类封装 - 调用父类ssrfHardcoded
   */
  async fetchSSRFHardcoded() {
    // 调用链: 子类 -> 父类ssrfHardcoded -> 漏洞点
    return await this.ssrfHardcoded();
  }
}

module.exports = VulnChild;
