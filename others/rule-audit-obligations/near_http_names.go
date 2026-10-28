package obligations

import "net/http"

// HeaderOnly changes headers without making an outgoing request or acquiring a response body.
func HeaderOnly() http.Header {
	header := make(http.Header)
	header.Set("X-Mode", "enabled")
	return header
}
