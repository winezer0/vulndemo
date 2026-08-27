package services

import "os/exec"

// Execute forwards a value to an external command API.
func Execute(value string) error {
	return exec.Command("sh", "-c", value).Run()
}
