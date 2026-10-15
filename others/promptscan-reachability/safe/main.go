package main

import (
	"net/http"
	"os/exec"
)

func main() {
	http.HandleFunc("/echo", echo)
	_ = http.ListenAndServe(":8080", nil)
}

func echo(w http.ResponseWriter, r *http.Request) {
	input := r.URL.Query().Get("value")
	output, err := exec.Command("echo", input).Output()
	if err != nil {
		http.Error(w, "command failed", http.StatusInternalServerError)
		return
	}
	_, _ = w.Write(output)
}
