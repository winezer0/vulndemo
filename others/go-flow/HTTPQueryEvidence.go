package goflow

import "net/http"

type HTTPFakeQuery struct{}

func (HTTPFakeQuery) Get(string) string { return "" }

type HTTPFakeURL struct{}

func (HTTPFakeURL) Query() HTTPFakeQuery { return HTTPFakeQuery{} }

type HTTPFakeQueryRequest struct{ URL HTTPFakeURL }

func inspectQuery(fake HTTPFakeQueryRequest, req *http.Request, key string) {
	fake.URL.Query().Get(key)
	req.URL.Query().Get(key)
}
