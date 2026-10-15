package sample

import "net/http"

type HTTPGroupedVarClient struct{}

func (HTTPGroupedVarClient) Get(string) {}

func callGroupedVar(url string) {
	var other, http HTTPGroupedVarClient
	http.Get(url)
	_ = other
}
