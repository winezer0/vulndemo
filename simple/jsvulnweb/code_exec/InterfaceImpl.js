const IVulnInterface = require('../interface/IVulnInterface');
const VulnParent = require('../common/VulnParent');

/**
 * 接口实现类 - CodeExecInterfaceImpl
 * 同时继承IVulnInterface接口和持有VulnParent引用
 */
class CodeExecInterfaceImpl extends IVulnInterface {
  constructor() {
    super();
    this.parent = new VulnParent();
  }

  /**
   * 实现接口方法 - 代码执行（外部可控数据源）
   * 调用链: 路由 -> impl.doExecCode -> parent.codeExecExternal -> eval
   */
  doExecCode(input) {
    return this.parent.codeExecExternal(input);
  }

  /**
   * 实现接口方法 - 代码执行（硬编码数据源）
   * 调用链: 路由 -> impl.doExecCodeHardcoded -> parent.codeExecHardcoded -> eval
   */
  doExecCodeHardcoded() {
    return this.parent.codeExecHardcoded();
  }

  // 以下方法占位，不使用但必须实现
  doExecCmd(input) { return null; }
  doExecCmdHardcoded() { return null; }
  async doFetchSSRF(url) { return null; }
  async doFetchSSRFHardcoded() { return null; }
}

module.exports = CodeExecInterfaceImpl;
