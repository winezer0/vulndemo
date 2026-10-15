package main

import (
	"net/http"
	"os/exec"
)

func main() {
	http.HandleFunc("/run", runCommand)
	_ = http.ListenAndServe(":8080", nil)
}

func runCommand(w http.ResponseWriter, r *http.Request) {
	input := r.URL.Query().Get("cmd")
	_ = exec.Command("sh", "-c", input).Run()
}
