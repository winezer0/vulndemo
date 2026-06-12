const VulnChild = require('../common/VulnChild');

const vulnChild = new VulnChild();

/**
 * 子类继承调用 - 命令执行（外部可控数据源）
 * 调用链: 路由 -> vulnChild.execCmd -> VulnParent.cmdExecExternal -> execSync
 */
function subclassCmdExec(input) {
  return vulnChild.execCmd(input);
}

/**
 * 子类继承调用 - 命令执行（硬编码数据源）
 * 调用链: 路由 -> vulnChild.execCmdHardcoded -> VulnParent.cmdExecHardcoded -> execSync
 */
function subclassCmdExecHardcoded() {
  return vulnChild.execCmdHardcoded();
}

module.exports = { subclassCmdExec, subclassCmdExecHardcoded };
