package externalhttp

import (
	"encoding/json"
	"example.invalid/opaque"
	"io"
)

// PartialReadRetainsContent keeps preexisting bytes when a reader writes only an unknown prefix.
func PartialReadRetainsContent(retainedInput []byte, reader io.Reader) {
	buffer := retainedInput
	io.ReadFull(reader, buffer)
	retainedOut := buffer[0]
	consume(retainedOut)
}

// CrossFunctionPartialRead retains bytes passed through a separate buffer alias.
func CrossFunctionPartialRead(crossInput []byte, reader io.Reader) {
	buffer := bufferAlias(crossInput)
	io.ReadFull(reader, buffer)
	crossOut := buffer[0]
	consume(crossOut)
}

// bufferAlias forwards the caller-owned slice without copying its contents.
func bufferAlias(buffer []byte) []byte { return buffer }

// PartialChunkRetainsContent keeps the untouched suffix after a bounded read.
func PartialChunkRetainsContent(partialInput []byte, reader io.Reader) {
	buffer := bufferAlias(partialInput)
	reader.Read(buffer[:2])
	partialOut := buffer[2]
	consume(partialOut)
}

// PartialDecodeRetainsContent keeps a previously assigned field if decoding omits it or fails.
func PartialDecodeRetainsContent(decodedInput string, reader io.Reader) {
	var value struct{ Value string }
	value.Value = decodedInput
	decoder := json.NewDecoder(reader)
	decoder.Decode(&value)
	decodedOut := value.Value
	consume(decodedOut)
}

// ReturnContent carries a concrete unknown output across an ordinary function return.
func ReturnContent(returnInput string) {
	returnOut := filledBuffer(returnInput)[0]
	consume(returnOut)
}

func filledBuffer(input string) []byte {
	buffer := make([]byte, 8)
	opaque.Fill(buffer, input)
	return buffer
}

// RepeatedCallback keeps every possible registration for the same actual callback.
func RepeatedCallback(repeatInput string) {
	callback := func(value string) {
		repeatOut := value
		consume(repeatOut)
	}
	opaque.Listen("fixed", callback)
	opaque.Listen(repeatInput, callback)
}

// OrderedAsyncRead keeps a completed earlier read independent of a later asynchronous mutation.
func OrderedAsyncRead(orderedInput string) {
	buffer := make([]byte, 8)
	orderedOut := buffer[0]
	go opaque.Fill(buffer, orderedInput)
	consume(orderedOut)
}
