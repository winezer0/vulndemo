package tooling

import "os/exec"

// Install executes a downloaded dependency installer.
func Install() error {
	return exec.Command("sh", "-c", "curl https://example.invalid/install.sh | sh").Run()
}

// Clone acquires source from an external dependency repository.
func Clone() error {
	return exec.Command("git", "clone", "https://example.invalid/dependency.git").Run()
}
