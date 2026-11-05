package fastfixture

import "github.com/valyala/fasthttp"

// ClientGet exercises an externally configured client.
func ClientGet(fastClientInput string, client *fasthttp.Client) {
	_, fastClientOut, _ := client.Get(nil, fastClientInput)
	consume(fastClientOut)
}

// HostGet exercises host-client response slots.
func HostGet(hostClientInput string, client *fasthttp.HostClient) {
	_, hostClientOut, _ := client.Get(nil, hostClientInput)
	consume(hostClientOut)
}

// ClientDo exercises request and response pointer contents.
func ClientDo(fastDoInput string, client *fasthttp.Client) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	request.SetRequestURI(fastDoInput)
	_ = client.Do(request, response)
	fastDoOut := response.Body()
	consume(fastDoOut)
	fasthttp.ReleaseRequest(request)
	fasthttp.ReleaseResponse(response)
}

// HostDo exercises mutable output on a host client.
func HostDo(hostDoInput string, client *fasthttp.HostClient) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	request.SetRequestURI(hostDoInput)
	_ = client.Do(request, response)
	hostDoOut := response.Body()
	consume(hostDoOut)
	fasthttp.ReleaseRequest(request)
	fasthttp.ReleaseResponse(response)
}

// PackageDo exercises the default package-level client.
func PackageDo(packageDoInput string) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	request.SetRequestURI(packageDoInput)
	_ = fasthttp.Do(request, response)
	packageDoOut := response.Body()
	consume(packageDoOut)
	fasthttp.ReleaseRequest(request)
	fasthttp.ReleaseResponse(response)
}

// IsolatedDo keeps independent response objects separate.
func IsolatedDo(doIsolatedInput string, client *fasthttp.Client) {
	request := fasthttp.AcquireRequest()
	changed := fasthttp.AcquireResponse()
	clean := fasthttp.AcquireResponse()
	request.SetRequestURI(doIsolatedInput)
	_ = client.Do(request, changed)
	doIsolatedOut := clean.Body()
	consume(doIsolatedOut)
}

// LaterDo rejects request configuration after the observed invocation.
func LaterDo(doLateInput string, client *fasthttp.Client) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	_ = client.Do(request, response)
	request.SetRequestURI(doLateInput)
	doLateOut := response.Body()
	consume(doLateOut)
}
