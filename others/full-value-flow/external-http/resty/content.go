package restyfixture

import (
	"io"

	"github.com/go-resty/resty/v2"
)

// RestyBody exercises a typed response and the Body accessor.
func RestyBody(bodyInput string) []byte {
	client := resty.New()
	request := client.R()
	response, _ := request.Get(bodyInput)
	bodyOut := response.Body()
	return bodyOut
}

// RestyString exercises the fluent factory chain and String accessor.
func RestyString(stringInput string) string {
	response, _ := resty.New().R().Post(stringInput)
	stringOut := response.String()
	return stringOut
}

// RestyRaw exercises Execute, an alias and the RawBody reader.
func RestyRaw(rawInput string) []byte {
	response, _ := resty.New().R().Execute("GET", rawInput)
	alias := response
	rawOut, _ := io.ReadAll(alias.RawBody())
	return rawOut
}

// RestyIsolation keeps two response instances independent.
func RestyIsolation(firstInput, secondInput string) []byte {
	request := resty.New().R()
	firstResponse, _ := request.Get(firstInput)
	secondResponse, _ := request.Post(secondInput)
	_ = firstResponse.Body()
	isolatedOut := secondResponse.Body()
	return isolatedOut
}

// RestyUnknown keeps decoding and custom response semantics conservative.
func RestyUnknown(unknownInput *resty.Response) interface{} {
	unknownOut := unknownInput.Result()
	return unknownOut
}
