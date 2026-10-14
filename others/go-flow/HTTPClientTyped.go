package goflow

import "net/http"

func inspectTypedHTTP(client http.Client, req *http.Request, url string) {
	client.Get(url)
	client.Do(req)
	http.DefaultClient.Get(url)
}
