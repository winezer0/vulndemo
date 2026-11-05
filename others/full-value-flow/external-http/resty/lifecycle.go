package restyfixture

import (
	"io"
	"net/http"
	"strings"

	"github.com/go-resty/resty/v2"
)

// RestyHook replaces response content with a captured request input.
func RestyHook(hookInput string) []byte {
	client := resty.New()
	client.OnAfterResponse(func(_ *resty.Client, response *resty.Response) error {
		response.SetBody([]byte(hookInput))
		return nil
	})
	response, _ := client.R().Get("https://example.invalid")
	hookOut := response.Body()
	return hookOut
}

// RestyTransport keeps custom response sources separate from the destination URL.
func RestyTransport(transportInput string) []byte {
	client := resty.New()
	client.SetTransport(contentTransport{value: transportInput})
	response, _ := client.R().Get("https://example.invalid")
	transportOut := response.Body()
	return transportOut
}

type contentTransport struct{ value string }

// RoundTrip supplies deterministic local response content without network access.
func (transport contentTransport) RoundTrip(request *http.Request) (*http.Response, error) {
	return &http.Response{StatusCode: 200, Body: io.NopCloser(strings.NewReader(transport.value)), Header: make(http.Header), Request: request}, nil
}

// RestyDynamic retains conditional writes through an alias.
func RestyDynamic(dynamicInput string, enabled bool) string {
	client := resty.New()
	alias := client
	if enabled {
		alias.SetHeader("X-Input", dynamicInput)
	}
	response, _ := client.R().Get("https://example.invalid")
	dynamicOut := response.String()
	return dynamicOut
}

// configureClient carries unknown configuration through a local callable.
func configureClient(client *resty.Client, value string) *resty.Client {
	client.SetHeader("X-Input", value)
	return client
}

// RestyConfiguredReturn retains configuration across a returned client.
func RestyConfiguredReturn(returnInput string) []byte {
	client := configureClient(resty.New(), returnInput)
	response, _ := client.R().Get("https://example.invalid")
	returnOut := response.Body()
	return returnOut
}

// RestyRetry keeps unverified retry callbacks conservative.
func RestyRetry(retryInput string) []byte {
	client := resty.New()
	client.SetRetryCount(1)
	client.AddRetryHook(func(response *resty.Response, _ error) {
		response.SetBody([]byte(retryInput))
	})
	response, _ := client.R().Get("https://example.invalid")
	retryOut := response.Body()
	return retryOut
}

// RestyBeforeHook retains captured inputs from a request middleware.
func RestyBeforeHook(beforeInput string) []byte {
	client := resty.New()
	client.OnBeforeRequest(func(_ *resty.Client, request *resty.Request) error {
		request.SetHeader("X-Input", beforeInput)
		return nil
	})
	response, _ := client.R().Get("https://example.invalid")
	beforeHookOut := response.Body()
	return beforeHookOut
}

// RestyRequestConfig retains request-specific configuration in fluent calls.
func RestyRequestConfig(requestInput string) string {
	request := resty.New().R()
	request.SetHeader("X-Input", requestInput)
	response, _ := request.Get("https://example.invalid")
	requestOut := response.String()
	return requestOut
}

// RestyRawFailure retains an actual reader result even when reads may fail.
func RestyRawFailure(failureInput string) ([]byte, error) {
	client := resty.New()
	client.SetDoNotParseResponse(true)
	response, requestError := client.R().Get(failureInput)
	if requestError != nil {
		return nil, requestError
	}
	failureOut, readError := io.ReadAll(response.RawBody())
	return failureOut, readError
}

// RestyConfigurationOrder keeps later configuration out of an earlier response.
func RestyConfigurationOrder(orderInput string) ([]byte, []byte) {
	client := resty.New()
	request := client.R()
	before, _ := request.Get("https://example.invalid")
	client.SetHeader("X-Input", orderInput)
	after, _ := request.Get("https://example.invalid")
	beforeOut := before.Body()
	afterOut := after.Body()
	return beforeOut, afterOut
}

// RestyLocalConfiguration retains a helper's side effect without depending on its return value.
func RestyLocalConfiguration(localInput string) []byte {
	client := resty.New()
	configureClient(client, localInput)
	response, _ := client.R().Get("https://example.invalid")
	localOut := response.Body()
	return localOut
}

// RestyIndependentClient keeps another client's configuration isolated.
func RestyIndependentClient(isolationInput string) []byte {
	configured := resty.New()
	configured.SetHeader("X-Input", isolationInput)
	clean := resty.New()
	response, _ := clean.R().Get("https://example.invalid")
	cleanOut := response.Body()
	return cleanOut
}

// RestyUnknownClient preserves input dependencies when client provenance is unavailable.
func RestyUnknownClient(unknownClient *resty.Client, unknownURL string) []byte {
	response, _ := unknownClient.R().Get(unknownURL)
	unknownClientOut := response.Body()
	return unknownClientOut
}

// RestyBranchClient retains configuration through a conditional receiver alias.
func RestyBranchClient(branchInput string, enabled bool) []byte {
	first := resty.New()
	second := resty.New()
	first.SetHeader("X-Input", branchInput)
	client := second
	if enabled {
		client = first
	}
	response, _ := client.R().Get("https://example.invalid")
	branchOut := response.Body()
	return branchOut
}

// RestyOverwrite keeps a later response-content mutation visible.
func RestyOverwrite(overwriteInput string) []byte {
	response, _ := resty.New().R().Get("https://example.invalid")
	response.SetBody([]byte(overwriteInput))
	overwriteOut := response.Body()
	return overwriteOut
}
