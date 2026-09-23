package main

import (
	"os"
	"os/exec"
)

func main() {
	run(os.Args[1])
}

func run(command string) {
	_ = exec.Command("sh", "-c", command).Run()
}
