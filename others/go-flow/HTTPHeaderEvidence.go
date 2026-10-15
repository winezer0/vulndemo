package goflow

import "net/http"

type HTTPFakeHeader struct{}

func (HTTPFakeHeader) Get(string) string { return "" }

type HTTPFakeRequest struct{ Header HTTPFakeHeader }

func inspectHeaders(fake HTTPFakeRequest, req *http.Request, key string) {
	fake.Header.Get(key)
	req.Header.Get(key)
}
