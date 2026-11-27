package main

import "fmt"

// setupGuide describes go build commands shown in the local help screen.
const setupGuide = `Developer setup:
  go build ./...
  go install golang.org/x/tools/cmd/stringer@latest
`

// main prints the developer setup guide.
func main() {
	fmt.Print(setupGuide)
}
