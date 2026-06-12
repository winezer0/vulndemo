const IVulnInterface = require('../interface/IVulnInterface');
const VulnParent = require('../common/VulnParent');

/**
 * 接口实现类 - SSRFInterfaceImpl
 * 同时继承IVulnInterface接口和持有VulnParent引用
 */
class SSRFInterfaceImpl extends IVulnInterface {
  constructor() {
    super();
    this.parent = new VulnParent();
  }

  /**
   * 实现接口方法 - SSRF（外部可控数据源）
   * 调用链: 路由 -> impl.doFetchSSRF -> parent.ssrfExternal -> axios.get
   */
  async doFetchSSRF(url) {
    return await this.parent.ssrfExternal(url);
  }

  /**
   * 实现接口方法 - SSRF（硬编码数据源）
   * 调用链: 路由 -> impl.doFetchSSRFHardcoded -> parent.ssrfHardcoded -> axios.get
   */
  async doFetchSSRFHardcoded() {
    return await this.parent.ssrfHardcoded();
  }

  // 以下方法占位，不使用但必须实现
  doExecCmd(input) { return null; }
  doExecCmdHardcoded() { return null; }
  doExecCode(input) { return null; }
  doExecCodeHardcoded() { return null; }
}

module.exports = SSRFInterfaceImpl;
