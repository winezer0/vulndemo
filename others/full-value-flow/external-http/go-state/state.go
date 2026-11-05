package httpstate

import "github.com/go-resty/resty/v2"

// LoopConfiguration may use the later configuration in the next iteration.
func LoopConfiguration(loopInput string) {
	client := resty.New()
	for range 2 {
		response, _ := client.R().Get("https://example.invalid")
		loopOut := response.Body()
		client.SetHeader("X", loopInput)
		consume(loopOut)
	}
}

// CapturedConfiguration mutates an escaped client through an invoked closure.
func CapturedConfiguration(captureInput string) {
	client := resty.New()
	configure := func() { client.SetHeader("X", captureInput) }
	configure()
	response, _ := client.R().Get("https://example.invalid")
	captureOut := response.Body()
	consume(captureOut)
}

// NestedConfiguration retains a client passed through multiple local helpers.
func NestedConfiguration(nestedInput string) {
	client := resty.New()
	configureOuter(client, nestedInput)
	response, _ := client.R().Get("https://example.invalid")
	nestedOut := response.Body()
	consume(nestedOut)
}

// configureOuter passes the shared client to the inner helper.
func configureOuter(client *resty.Client, input string) { configureInner(client, input) }

// configureInner writes an actual configuration value.
func configureInner(client *resty.Client, input string) { client.SetHeader("X", input) }

// UnknownCallback retains a callback produced by an unknown external operation.
func UnknownCallback(unknownCallbackInput string, factory func(string) func(*resty.Client, *resty.Response) error) {
	client := resty.New()
	client.OnAfterResponse(factory(unknownCallbackInput))
	response, _ := client.R().Get("https://example.invalid")
	unknownCallbackOut := response.Body()
	consume(unknownCallbackOut)
}

// consume keeps fixture outputs observable without a library-specific sink.
func consume(any) {}

// AsyncConfiguration retains a possible concurrent captured mutation.
func AsyncConfiguration(asyncConfigInput string) {
	client := resty.New()
	go func() { client.SetHeader("X", asyncConfigInput) }()
	response, _ := client.R().Get("https://example.invalid")
	asyncConfigOut := response.Body()
	consume(asyncConfigOut)
}

// UninvokedConfiguration does not execute its captured mutation.
func UninvokedConfiguration(uninvokedInput string) {
	client := resty.New()
	configure := func() { client.SetHeader("X", uninvokedInput) }
	_ = configure
	response, _ := client.R().Get("https://example.invalid")
	uninvokedOut := response.Body()
	consume(uninvokedOut)
}

// LaterCapturedConfiguration mutates only after the observed response.
func LaterCapturedConfiguration(laterCaptureInput string) {
	client := resty.New()
	configure := func() { client.SetHeader("X", laterCaptureInput) }
	response, _ := client.R().Get("https://example.invalid")
	laterCaptureOut := response.Body()
	configure()
	consume(laterCaptureOut)
}

// IsolatedConfiguration keeps two independent captured objects separate.
func IsolatedConfiguration(isolatedInput string) {
	client := resty.New()
	other := resty.New()
	configure := func() { other.SetHeader("X", isolatedInput) }
	configure()
	response, _ := client.R().Get("https://example.invalid")
	isolatedOut := response.Body()
	consume(isolatedOut)
}
