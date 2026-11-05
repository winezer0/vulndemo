package fastfixture

import (
	"net"

	"github.com/valyala/fasthttp"
)

// ZeroClientGet separates the URL from the independent response body of a default client.
func ZeroClientGet(zeroInput string) {
	client := &fasthttp.Client{}
	zeroStatus, zeroBody, zeroError := client.Get(nil, zeroInput)
	consume(zeroStatus)
	consume(zeroBody)
	consume(zeroError)
}

// ZeroClientDo binds the actual mutable response of a default client.
func ZeroClientDo(zeroDoInput string) {
	client := &fasthttp.Client{}
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	request.SetRequestURI(zeroDoInput)
	_ = client.Do(request, response)
	zeroDoOut := response.Body()
	consume(zeroDoOut)
}

// CustomClient retains the possible payload of an opaque user-supplied transport.
func CustomClient(customInput string, dial func(string) (net.Conn, error)) {
	client := &fasthttp.Client{}
	client.Dial = dial
	_, customOut, _ := client.Get(nil, customInput)
	consume(customOut)
}

// UnexecutedResponse has no receive operation that could define response content.
func UnexecutedResponse(unexecutedInput string) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	request.SetRequestURI(unexecutedInput)
	unexecutedOut := response.Body()
	consume(unexecutedOut)
}

// FailedDoPrior keeps earlier content possible when a request fails before response reset.
func FailedDoPrior(priorInput []byte) {
	client := &fasthttp.Client{}
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	response.SetBody(priorInput)
	request.SetRequestURI("ftp://local.invalid/")
	_ = client.Do(request, response)
	priorOut := response.Body()
	consume(priorOut)
}

// FailedGetPrior retains the caller buffer when an unsupported URI fails before reset.
func FailedGetPrior(getPriorInput []byte) {
	_, getPriorOut, _ := fasthttp.Get(getPriorInput, "ftp://local.invalid/")
	consume(getPriorOut)
}

// FailedClientGetPrior checks the same failure contract on a default receiver.
func FailedClientGetPrior(clientPriorInput []byte) {
	client := &fasthttp.Client{}
	_, clientPriorOut, _ := client.Get(clientPriorInput, "ftp://local.invalid/")
	consume(clientPriorOut)
}

// ReleasedResponse checks the pool boundary without relying on a particular pooled allocation.
func ReleasedResponse(poolInput []byte) {
	response := fasthttp.AcquireResponse()
	response.SetBody(poolInput)
	fasthttp.ReleaseResponse(response)
	next := fasthttp.AcquireResponse()
	poolOut := next.Body()
	consume(poolOut)
	fasthttp.ReleaseResponse(next)
}

// ShadowedNilBuffer uses an ordinary local binding whose name shadows predeclared nil.
func ShadowedNilBuffer(nilShadowInput []byte) {
	nil := nilShadowInput
	_, nilShadowOut, _ := fasthttp.Get(nil, "ftp://local.invalid/")
	consume(nilShadowOut)
}

// ReusedResponse keeps earlier body contents possible across failed and repeated requests.
func ReusedResponse(reuseFirstInput, reuseSecondInput string, reusePriorInput []byte, client *fasthttp.Client) {
	request := fasthttp.AcquireRequest()
	response := fasthttp.AcquireResponse()
	response.SetBody(reusePriorInput)
	request.SetRequestURI(reuseFirstInput)
	_ = client.Do(request, response)
	reuseFirstOut := response.Body()
	consume(reuseFirstOut)
	request.SetRequestURI(reuseSecondInput)
	_ = client.Do(request, response)
	reuseSecondOut := response.Body()
	consume(reuseSecondOut)
	fasthttp.ReleaseRequest(request)
	fasthttp.ReleaseResponse(response)
}

// UnknownClientPair keeps separate requests conservative when client parameters may alias.
func UnknownClientPair(pairFirstInput, pairSecondInput string, first, second *fasthttp.Client) {
	_, pairFirstOut, _ := first.Get(nil, pairFirstInput)
	_, pairSecondOut, _ := second.Get(nil, pairSecondInput)
	consume(pairFirstOut)
	consume(pairSecondOut)
}
