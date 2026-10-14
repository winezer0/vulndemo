package goflow

import "net/http"

type FakeHTTPClient struct{}

func (FakeHTTPClient) Get(string) {}

func inspectHTTP(fakeClient FakeHTTPClient, url string) {
	fakeClient.Get(url)
	http.Get(url)
}

func inspectShadow(fakeClient FakeHTTPClient, url string) {
	http.Get(url)
	http := fakeClient
	http.Get(url)
	_ = http
}

func inspectInner(fakeClient FakeHTTPClient, url string) {
	{ http := fakeClient; http.Get(url) }
	http.Get(url)
}
