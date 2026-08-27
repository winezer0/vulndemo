package main

import (
	"net/http"

	"example.com/crossfile/internal/handlers"
)

func main() {
	http.HandleFunc("/run", handlers.Handle)
	_ = http.ListenAndServe(":8080", nil)
}
