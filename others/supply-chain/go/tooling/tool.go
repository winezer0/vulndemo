package tooling

import (
	"context"
	osexec "os/exec"
)

// Generate invokes the Go toolchain to execute a project generator.
func Generate(ctx context.Context) error {
	return osexec.CommandContext(ctx, "go", "generate", "./...").Run()
}
