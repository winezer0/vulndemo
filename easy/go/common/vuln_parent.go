package common

import (
	"os/exec"
)

type VulnParent struct{}

func (VulnParent) Exec(cmd string) string {
	out, _ := exec.Command("sh", "-c", cmd).CombinedOutput()
	return string(out)
}
