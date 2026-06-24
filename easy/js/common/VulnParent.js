const { execSync } = require('child_process');

class VulnParent {
    exec(cmd) {
        execSync(cmd);
    }
}

module.exports = VulnParent;
