const IVulnInterface = require('../interface/IVulnInterface');
const VulnParent = require('../common/VulnParent');

/**
 * 接口实现类 - CmdExecInterfaceImpl
 * 同时继承IVulnInterface接口和持有VulnParent引用
 */
class CmdExecInterfaceImpl extends IVulnInterface {
  constructor() {
    super();
    this.parent = new VulnParent();
  }

  /**
   * 实现接口方法 - 命令执行（外部可控数据源）
   * 调用链: 路由 -> impl.doExecCmd -> parent.cmdExecExternal -> execSync
   */
  doExecCmd(input) {
    return this.parent.cmdExecExternal(input);
  }

  /**
   * 实现接口方法 - 命令执行（硬编码数据源）
   * 调用链: 路由 -> impl.doExecCmdHardcoded -> parent.cmdExecHardcoded -> execSync
   */
  doExecCmdHardcoded() {
    return this.parent.cmdExecHardcoded();
  }

  // 以下方法占位，不使用但必须实现
  doExecCode(input) { return null; }
  doExecCodeHardcoded() { return null; }
  async doFetchSSRF(url) { return null; }
  async doFetchSSRFHardcoded() { return null; }
}

module.exports = CmdExecInterfaceImpl;
