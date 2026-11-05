package externalhttp

import (
	"bytes"
	"example.invalid/opaque"
	"io"
	"net/http"
)

// ReceiverContent reads a post-call content version through an ordinary method.
func ReceiverContent(receiverInput string) {
	var buffer bytes.Buffer
	opaque.Fill(&buffer, receiverInput)
	receiverOut := buffer.String()
	consume(receiverOut)
}

// CopyContent exercises the exact source argument of a modeled output write.
func CopyContent(copyInput io.Reader) {
	var buffer bytes.Buffer
	io.Copy(&buffer, copyInput)
	copyOut := buffer.String()
	consume(copyOut)
}

// HTTPErrorContent keeps request-bearing error payloads outside the complete response-content slot.
func HTTPErrorContent(errorInput string) {
	_, errorOut := http.Get(errorInput)
	consume(errorOut)
}

// HTTPPostContent exercises the top-level response model with a request body argument.
func HTTPPostContent(postInput string) {
	response, _ := http.Post(postInput, "text/plain", nil)
	postOut, _ := io.ReadAll(response.Body)
	consume(postOut)
}

// HTTPClientContent exercises a resolved client receiver.
func HTTPClientContent(clientInput string) {
	client := &http.Client{}
	response, _ := client.Get(clientInput)
	clientOut, _ := io.ReadAll(response.Body)
	consume(clientOut)
}

// HTTPDoContent exercises request objects separately from returned response content.
func HTTPDoContent(doInput string) {
	client := &http.Client{}
	request, _ := http.NewRequest("GET", doInput, nil)
	response, _ := client.Do(request)
	doOut, _ := io.ReadAll(response.Body)
	consume(doOut)
}

// UnknownContent exercises conservative fallback independently of precise HTTP summaries.
func UnknownContent(unknownInput string) {
	response := opaque.Fetch(unknownInput)
	unknownOut := opaque.Read(response.Body)
	consume(unknownOut)
}

// OutputContent checks external mutations through a slice argument.
func OutputContent(outputInput string) {
	buffer := make([]byte, 8)
	opaque.Fill(buffer, outputInput)
	outputOut := buffer[0]
	consume(outputOut)
}

// BeforeOutput excludes a later mutation from an earlier read.
func BeforeOutput(beforeInput string) {
	buffer := make([]byte, 8)
	beforeOut := buffer[0]
	opaque.Fill(buffer, beforeInput)
	consume(beforeOut)
}

// IndependentOutput keeps distinct buffers separate.
func IndependentOutput(distinctInput string) {
	first := make([]byte, 8)
	second := make([]byte, 8)
	opaque.Fill(first, distinctInput)
	distinctOut := second[0]
	consume(distinctOut)
}

// AliasOutput preserves the actual shared slice identity.
func AliasOutput(aliasInput string) {
	buffer := make([]byte, 8)
	alias := buffer
	opaque.Fill(alias, aliasInput)
	aliasOut := buffer[0]
	consume(aliasOut)
}

// OverwrittenOutput excludes a freshly allocated replacement buffer.
func OverwrittenOutput(replacedInput string) {
	buffer := make([]byte, 8)
	opaque.Fill(buffer, replacedInput)
	buffer = make([]byte, 8)
	replacedOut := buffer[0]
	consume(replacedOut)
}

// HTTPContent retains request provenance through a modeled response field and reader.
// CallbackContent retains possible external callback inputs without claiming execution is verified.
func CallbackContent(callbackInput string) {
	opaque.Listen(callbackInput, func(value string) {
		callbackOut := value
		consume(callbackOut)
	})
}

// ReturnedCallback preserves a captured value through a possible external callback invocation.
func ReturnedCallback(cbReturnInput string) {
	callback := func() string { return cbReturnInput }
	cbReturnOut := opaque.Run(callback)
	consume(cbReturnOut)
}

// CallbackIsolation keeps separate registrations and lexical callbacks apart.
func CallbackIsolation(cbIsolatedInput string) {
	opaque.Listen(cbIsolatedInput, func(value string) { consume(value) })
	opaque.Listen("fixed", func(value string) {
		cbIsolatedOut := value
		consume(cbIsolatedOut)
	})
}

// AsyncOutput carries an actual post-call value through the existing channel protocol.
func AsyncOutput(asyncInput string) {
	values := make(chan string, 1)
	go func() {
		var output string
		opaque.Fill(&output, asyncInput)
		values <- output
	}()
	asyncOut := <-values
	consume(asyncOut)
}

// HTTPContent retains request provenance through a modeled response field and reader.
func HTTPContent(httpInput string) {
	response, _ := http.Get(httpInput)
	httpOut, _ := io.ReadAll(response.Body)
	consume(httpOut)
}

// IndependentResponse keeps distinct invocation results separate.
func IndependentResponse(isolatedInput string) {
	first, _ := http.Get(isolatedInput)
	second, _ := http.Get("https://example.invalid/fixed")
	isolatedOut, _ := io.ReadAll(second.Body)
	consume(first, isolatedOut)
}

// consume keeps observable fixture values without performing network requests in tests.
func consume(values ...any) {}
