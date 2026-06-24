const VulnChild = require('./common/VulnChild');
const InterfaceImpl = require('./cmd_exec/InterfaceImpl');

class FuncCall {
    static runByChild(cmd) {
        const child = new VulnChild();
        child.exec(cmd);
    }

    static runByInterface(cmd) {
        const runner = new InterfaceImpl();
        runner.run(cmd);
    }
}

module.exports = FuncCall;
