using System.Diagnostics;

namespace common {
    public class VulnParent {
        public void Exec(string cmd) {
            Process.Start("cmd.exe", "/c " + cmd);
        }
    }
}
