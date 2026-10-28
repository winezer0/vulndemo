package obligations

import (
	pack "archive/tar"
	zipper "compress/gzip"
	"context"
	stream "io"
	"net"
	web "net/http"
	fs "os"
	"time"
)

var password = "local-credential-7439"

// Consume exposes library operations through arbitrary import aliases.
func Consume(input stream.Reader, output stream.Writer, path, address string) {
	decoded, _ := zipper.NewReader(input)
	archive := pack.NewReader(decoded)
	_, _ = archive.Next()
	_, _ = stream.ReadAll(input)
	_, _ = stream.Copy(output, input)
	file, _ := fs.OpenFile(path, fs.O_CREATE|fs.O_RDWR, 0600)
	_ = file
	_, _ = web.Get(address)
	_, _ = net.Dial("tcp", address)
}

// BoundedConsume retains candidate signals despite explicit controls.
func BoundedConsume(ctx context.Context, input stream.Reader, path, address string) {
	_, _ = stream.ReadAll(stream.LimitReader(input, 4096))
	file, err := fs.Open(path)
	if err == nil {
		defer file.Close()
	}
	request, _ := web.NewRequestWithContext(ctx, "GET", address, nil)
	client := web.Client{Timeout: time.Second}
	response, err := client.Do(request)
	if err == nil {
		defer response.Body.Close()
	}
	file, err = fs.OpenFile(path, fs.O_CREATE|fs.O_EXCL|fs.O_WRONLY, 0600)
	if err == nil {
		defer file.Close()
	}
}

var shared bool

// Launch gives state analysis an execution anchor, not proof of a race.
func Launch() {
	go func() { shared = true }()
	_ = shared
}

// LaunchOwned demonstrates that task creation alone is not a vulnerability.
func LaunchOwned() {
	done := make(chan bool, 1)
	go func() { done <- true }()
	<-done
}

type mode int

const DebugMode mode = 1
const ReleaseMode mode = 0

type runtimeConfig struct{}

// SetMode provides a framework-independent configuration fixture.
func (runtimeConfig) SetMode(mode) {}

// Configure produces a debug signal independently of any specific framework.
func Configure() {
	server := runtimeConfig{}
	server.SetMode(DebugMode)
}
