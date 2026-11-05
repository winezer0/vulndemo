package restyfixture

import (
	"errors"
	"io"
	"net/http"
	"strings"
	"testing"

	"github.com/go-resty/resty/v2"
)

type roundTripFunc func(*http.Request) (*http.Response, error)

// RoundTrip delegates deterministic local transport behavior.
func (transport roundTripFunc) RoundTrip(request *http.Request) (*http.Response, error) {
	return transport(request)
}

type failingReader struct{ delivered bool }

// Read returns a partial body followed by an explicit failure.
func (reader *failingReader) Read(buffer []byte) (int, error) {
	if reader.delivered {
		return 0, errors.New("body failure")
	}
	reader.delivered = true
	return copy(buffer, "partial"), nil
}

// Close releases the local test reader.
func (*failingReader) Close() error { return nil }

// TestRestyLifecycleRuntime checks the pinned library's observable lifecycle without external network requests.
func TestRestyLifecycleRuntime(t *testing.T) {
	t.Run("response hook", func(t *testing.T) {
		client := resty.New().SetTransport(contentTransport{value: "transport"})
		client.OnAfterResponse(func(_ *resty.Client, response *resty.Response) error { response.SetBody([]byte("hook")); return nil })
		response, err := client.R().Get("http://local.invalid")
		if err != nil || response.String() != "hook" || string(response.Body()) != "hook" {
			t.Fatalf("response=%v error=%v", response, err)
		}
	})
	t.Run("before middleware transport", func(t *testing.T) {
		client := resty.New().SetTransport(roundTripFunc(func(request *http.Request) (*http.Response, error) {
			return &http.Response{StatusCode: 200, Header: make(http.Header), Body: io.NopCloser(strings.NewReader(request.Header.Get("X-Input"))), Request: request}, nil
		}))
		client.OnBeforeRequest(func(_ *resty.Client, request *resty.Request) error {
			request.SetHeader("X-Input", "before")
			return nil
		})
		response, err := client.R().Get("http://local.invalid")
		if err != nil || response.String() != "before" {
			t.Fatalf("response=%v error=%v", response, err)
		}
	})
	t.Run("retry hook", func(t *testing.T) {
		attempts, hooks := 0, 0
		client := resty.New().SetRetryCount(1).SetTransport(roundTripFunc(func(request *http.Request) (*http.Response, error) {
			attempts++
			return &http.Response{StatusCode: 503, Header: make(http.Header), Body: io.NopCloser(strings.NewReader("retry")), Request: request}, nil
		}))
		client.AddRetryCondition(func(response *resty.Response, _ error) bool { return response.StatusCode() == 503 })
		client.AddRetryHook(func(_ *resty.Response, _ error) { hooks++ })
		response, err := client.R().Get("http://local.invalid")
		if err != nil || response.String() != "retry" || attempts != 2 || hooks == 0 {
			t.Fatalf("attempts=%d hooks=%d response=%v error=%v", attempts, hooks, response, err)
		}
	})
	t.Run("raw partial read", func(t *testing.T) {
		client := resty.New().SetDoNotParseResponse(true).SetTransport(roundTripFunc(func(request *http.Request) (*http.Response, error) {
			return &http.Response{StatusCode: 200, Header: make(http.Header), Body: &failingReader{}, Request: request}, nil
		}))
		response, err := client.R().Get("http://local.invalid")
		if err != nil {
			t.Fatal(err)
		}
		reader := response.RawBody()
		defer func() {
			if closeErr := reader.Close(); closeErr != nil {
				t.Error(closeErr)
			}
		}()
		body, readErr := io.ReadAll(reader)
		if string(body) != "partial" || readErr == nil {
			t.Fatalf("body=%q error=%v", body, readErr)
		}
	})
	t.Run("pre request failure", func(t *testing.T) {
		sentinel := errors.New("before failure")
		client := resty.New().SetTransport(contentTransport{value: "unused"})
		client.OnBeforeRequest(func(_ *resty.Client, _ *resty.Request) error { return sentinel })
		response, err := client.R().Get("http://local.invalid")
		if response != nil || !errors.Is(err, sentinel) {
			t.Fatalf("response=%v error=%v", response, err)
		}
	})
}
