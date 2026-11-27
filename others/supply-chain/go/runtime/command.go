package runtime

import "os/exec"

// Command runs application input and requires command-injection review.
func Command(input string) error {
	return exec.Command("sh", "-c", input).Run()
}

// A go:generate mention is documentation rather than an executable directive.
