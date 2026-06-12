package main

import (
	"net/http"
)

func main() {
	http.HandleFunc("/exec", execHandler)
	http.ListenAndServe(":8080", nil)
}

func execHandler(w http.ResponseWriter, r *http.Request) {
	cmd := r.URL.Query().Get("cmd")
	mode := r.URL.Query().Get("mode")
	if mode == "impl" {
		_, _ = w.Write([]byte(RunByInterface(cmd)))
		return
	}
	_, _ = w.Write([]byte(RunByChild(cmd)))
}
