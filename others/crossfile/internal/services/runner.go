package services

import "os/exec"

// Runner abstracts command execution for interface-dispatch checks.
type Runner interface {
	Run(value string) error
}

// ShellRunner implements Runner with the command execution API.
type ShellRunner struct{}

// Run executes a shell command.
func (ShellRunner) Run(value string) error {
	return exec.Command("sh", "-c", value).Run()
}

// NewShellRunner returns the interface implementation used by the handler.
func NewShellRunner() Runner {
	return ShellRunner{}
}

// RunWithInterface dispatches through the Runner interface.
func RunWithInterface(r Runner, value string) error {
	return r.Run(value)
}
