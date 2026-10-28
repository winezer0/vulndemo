package obligations

import (
	"archive/tar"
	"compress/gzip"
	"context"
	"io"
	"net"
	"net/http"
	"os"
	"time"
)

// ControlledOperations gives verification explicit bounds and ownership facts.
func ControlledOperations(ctx context.Context, input io.Reader, output io.Writer, path, address string) {
	_, _ = io.ReadAll(io.LimitReader(input, 4096))
	_, _ = io.Copy(output, io.LimitReader(input, 4096))
	decoded, err := gzip.NewReader(input)
	if err == nil {
		defer decoded.Close()
		archive := tar.NewReader(io.LimitReader(decoded, 4096))
		for count := 0; count < 4; count++ {
			if _, err := archive.Next(); err != nil { break }
		}
	}
	file, err := os.OpenFile(path, os.O_CREATE|os.O_EXCL|os.O_WRONLY, 0600)
	if err == nil { defer file.Close() }
	client := http.Client{Timeout: time.Second}
	request, _ := http.NewRequestWithContext(ctx, "GET", address, nil)
	response, err := client.Do(request)
	if err == nil { defer response.Body.Close() }
	dialer := net.Dialer{Timeout: time.Second}
	connection, err := dialer.DialContext(ctx, "tcp", address)
	if err == nil { defer connection.Close() }
	done := make(chan bool, 1)
	go func() { done <- true }()
	<-done
}
