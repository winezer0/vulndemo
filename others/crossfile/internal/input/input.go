package input

import "net/http"

// FromRequest returns request-controlled data for returns_to and value-flow checks.
func FromRequest(r *http.Request) string {
	return r.URL.Query().Get("cmd")
}
