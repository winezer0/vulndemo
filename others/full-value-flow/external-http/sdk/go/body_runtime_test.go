package httpcontracts

import (
	"context"
	"errors"
	"io"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

type fixedTransport struct{}

// RoundTrip supplies deterministic custom content without performing a network request.
func (fixedTransport) RoundTrip(request *http.Request) (*http.Response, error) {
	return &http.Response{StatusCode: 200, Header: make(http.Header), Body: io.NopCloser(strings.NewReader("custom-body")), Request: request}, nil
}

// closeBody checks response cleanup without hiding transport errors.
func closeBody(t *testing.T, body io.ReadCloser) {
	t.Helper()
	if err := body.Close(); err != nil {
		t.Error(err)
	}
}

// TestNetHTTPRuntime checks actual Body, status, partial read and cancellation semantics.
func TestNetHTTPRuntime(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {
		status, body := http.StatusCreated, "fixed-go-body"
		if request.URL.Path == "/error" {
			status = http.StatusInternalServerError
		}
		if request.URL.Path == "/short" {
			writer.Header().Set("Content-Length", "100")
			body = "prefix"
		}
		writer.WriteHeader(status)
		if _, err := io.WriteString(writer, body); err != nil {
			t.Error(err)
		}
	}))
	t.Cleanup(server.Close)
	for _, sample := range []struct {
		name, path, body string
		status           int
		readError        error
	}{
		{"body", "/fixed", "fixed-go-body", http.StatusCreated, nil},
		{"HTTP error body", "/error", "fixed-go-body", http.StatusInternalServerError, nil},
		{"partial body", "/short", "prefix", http.StatusCreated, io.ErrUnexpectedEOF},
	} {
		t.Run(sample.name, func(t *testing.T) {
			response, err := http.Get(server.URL + sample.path)
			if err != nil {
				t.Fatal(err)
			}
			defer closeBody(t, response.Body)
			body, readErr := io.ReadAll(response.Body)
			if response.StatusCode != sample.status || string(body) != sample.body || !errors.Is(readErr, sample.readError) {
				t.Fatalf("status=%d body=%q error=%v", response.StatusCode, body, readErr)
			}
		})
	}
	t.Run("cancelled", func(t *testing.T) {
		ctx, cancel := context.WithCancel(context.Background())
		cancel()
		request, err := http.NewRequestWithContext(ctx, http.MethodGet, server.URL, nil)
		if err != nil {
			t.Fatal(err)
		}
		response, err := http.DefaultClient.Do(request)
		if response != nil || !errors.Is(err, context.Canceled) {
			t.Fatalf("response=%v error=%v", response, err)
		}
	})
	t.Run("custom transport", func(t *testing.T) {
		client := &http.Client{Transport: fixedTransport{}}
		response, err := client.Get("http://fixture.invalid/unused")
		if err != nil {
			t.Fatal(err)
		}
		defer closeBody(t, response.Body)
		body, err := io.ReadAll(response.Body)
		if err != nil || string(body) != "custom-body" {
			t.Fatalf("body=%q error=%v", body, err)
		}
	})
}
