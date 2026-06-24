const VulnChild = require('../common/VulnChild');

class InterfaceImpl extends VulnChild {
    run(cmd) {
        this.exec(cmd);
    }
}

module.exports = InterfaceImpl;
