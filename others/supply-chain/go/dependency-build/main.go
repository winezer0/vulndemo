package main

import (
	"log"
	"os/exec"
)

type State int

const Pending State = 0

// main runs the project enum generator.
func main() {
	command := exec.Command("go", "run", "golang.org/x/tools/cmd/stringer@latest", "-type", "State")
	if err := command.Run(); err != nil {
		log.Fatal(err)
	}
}
