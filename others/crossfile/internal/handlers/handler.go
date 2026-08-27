package handlers

import (
	"net/http"

	"example.com/crossfile/internal/input"
	"example.com/crossfile/internal/services"
)

// Handle is the cross-file entry function used by the call-chain examples.
func Handle(w http.ResponseWriter, r *http.Request) {
	value := input.FromRequest(r)
	_ = services.Execute(value)
	services.Query(value)

	runner := services.NewShellRunner()
	_ = services.RunWithInterface(runner, value)

	service := services.NewService()
	service.Execute(value)
	_ = w
}
