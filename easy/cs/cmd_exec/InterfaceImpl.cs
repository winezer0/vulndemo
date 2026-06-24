using common;
using iface;

namespace cmd_exec {
    public class InterfaceImpl : VulnChild, IVulnRunner {
        public void Run(string cmd) {
            Exec(cmd);
        }
    }
}
