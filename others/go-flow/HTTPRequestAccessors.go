package goflow

import "net/http"

type HTTPFakeAccessor struct{}

func (HTTPFakeAccessor) FormValue(string) string { return "" }
func (HTTPFakeAccessor) PathValue(string) string { return "" }

func inspectRequestAccessors(fake HTTPFakeAccessor, req *http.Request, key string) {
	fake.FormValue(key)
	req.FormValue(key)
	fake.PathValue(key)
	req.PathValue(key)
}
