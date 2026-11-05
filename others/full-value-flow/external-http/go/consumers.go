package externalhttp

import (
	"bufio"
	"encoding/json"
	"example.invalid/opaque"
	"io"
)

// ReadContent retains the actual returned byte prefix even when an error is also returned.
func ReadContent(readInput io.Reader) {
	buffer := make([]byte, 32)
	n, _ := readInput.Read(buffer)
	readOut := buffer[:n]
	consume(readOut)
}

// FullReadContent uses the declared reader and output buffer slots.
func FullReadContent(fullInput io.Reader) {
	buffer := make([]byte, 32)
	n, _ := io.ReadFull(fullInput, buffer)
	fullOut := buffer[:n]
	consume(fullOut)
}

// LeastReadContent includes the minimum-count argument without treating it as content.
func LeastReadContent(leastInput io.Reader) {
	buffer := make([]byte, 32)
	n, _ := io.ReadAtLeast(leastInput, buffer, 4)
	leastOut := buffer[:n]
	consume(leastOut)
}

// ScannerContent binds token text to the actual scanner receiver.
func ScannerContent(scannerInput io.Reader) {
	scanner := bufio.NewScanner(scannerInput)
	scanner.Scan()
	scannerOut := scanner.Text()
	consume(scannerOut)
}

// ScannerBytesContent binds byte tokens independently from scanner status.
func ScannerBytesContent(tokenInput io.Reader) {
	scanner := bufio.NewScanner(tokenInput)
	scanner.Scan()
	tokenOut := scanner.Bytes()
	consume(tokenOut)
}

// DecodeContent binds receiver content to the object passed by reference.
func DecodeContent(decodeInput io.Reader) {
	decoder := json.NewDecoder(decodeInput)
	var value struct{ Value string }
	decoder.Decode(&value)
	decodeOut := value.Value
	consume(decodeOut)
}

// StrongContentOverwrite removes a prior unknown write only for the overwritten exact element.
func StrongContentOverwrite(overwriteInput string) {
	buffer := make([]byte, 8)
	opaque.Fill(buffer, overwriteInput)
	buffer[0] = 'x'
	overwriteOut := buffer[0]
	consume(overwriteOut)
}

// BranchContent retains both actually possible storage targets of a conditional alias.
func BranchContent(branchInput string, choose bool) {
	first, second := make([]byte, 8), make([]byte, 8)
	value := first
	if choose {
		value = second
	}
	opaque.Fill(value, branchInput)
	branchOut := first[0]
	consume(branchOut)
}

// ReadCountContent separates a returned count from the reader's content value.
func ReadCountContent(countInput io.Reader) {
	buffer := make([]byte, 8)
	countOut, _ := io.ReadFull(countInput, buffer)
	consume(countOut)
}

// IndependentScannerContent keeps token content from independent scanners separate.
func IndependentScannerContent(cleanInput io.Reader) {
	first := bufio.NewScanner(cleanInput)
	second := bufio.NewScanner(nil)
	first.Scan()
	second.Scan()
	cleanOut := second.Text()
	consume(cleanOut)
}
