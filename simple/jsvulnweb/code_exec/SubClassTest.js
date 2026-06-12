const VulnChild = require('../common/VulnChild');

const vulnChild = new VulnChild();

/**
 * 子类继承调用 - 代码执行（外部可控数据源）
 * 调用链: 路由 -> vulnChild.execCode -> VulnParent.codeExecExternal -> eval
 */
function subclassCodeExec(input) {
  return vulnChild.execCode(input);
}

/**
 * 子类继承调用 - 代码执行（硬编码数据源）
 * 调用链: 路由 -> vulnChild.execCodeHardcoded -> VulnParent.codeExecHardcoded -> eval
 */
function subclassCodeExecHardcoded() {
  return vulnChild.execCodeHardcoded();
}

module.exports = { subclassCodeExec, subclassCodeExecHardcoded };
