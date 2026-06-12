const VulnChild = require('../common/VulnChild');

const vulnChild = new VulnChild();

/**
 * 子类继承调用 - SSRF（外部可控数据源）
 * 调用链: 路由 -> vulnChild.fetchSSRF -> VulnParent.ssrfExternal -> axios.get
 */
async function subclassSSRF(url) {
  return await vulnChild.fetchSSRF(url);
}

/**
 * 子类继承调用 - SSRF（硬编码数据源）
 * 调用链: 路由 -> vulnChild.fetchSSRFHardcoded -> VulnParent.ssrfHardcoded -> axios.get
 */
async function subclassSSRFHardcoded() {
  return await vulnChild.fetchSSRFHardcoded();
}

module.exports = { subclassSSRF, subclassSSRFHardcoded };
