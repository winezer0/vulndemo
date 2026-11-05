package fastfixture

import (
	"errors"
	"net"
	"net/http"
	"net/http/httptest"
	"net/url"
	"testing"
	"time"

	"github.com/valyala/fasthttp"
)

// TestSDKResponseContent exercises actual v1.58.0 responses against a local fixed-content server.
func TestSDKResponseContent(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusCreated)
		if _, err := w.Write([]byte("fixed-response-content")); err != nil {
			t.Error(err)
		}
	}))
	defer server.Close()
	client := &fasthttp.Client{}
	status, body, err := client.Get(nil, server.URL+"/request-marker")
	if err != nil || status != http.StatusCreated || string(body) != "fixed-response-content" {
		t.Fatalf("client response: status=%d body=%q err=%v", status, body, err)
	}
	request, response := fasthttp.AcquireRequest(), fasthttp.AcquireResponse()
	defer fasthttp.ReleaseRequest(request)
	defer fasthttp.ReleaseResponse(response)
	request.SetRequestURI(server.URL)
	if err := fasthttp.Do(request, response); err != nil || string(response.Body()) != "fixed-response-content" {
		t.Fatalf("package output: body=%q err=%v", response.Body(), err)
	}
	response.Reset()
	if err := client.Do(request, response); err != nil || string(response.Body()) != "fixed-response-content" {
		t.Fatalf("client output: body=%q err=%v", response.Body(), err)
	}
}

// TestSDKPoolReleaseResetsContent checks actual response and request pool boundaries.
func TestSDKPoolReleaseResetsContent(t *testing.T) {
	response := fasthttp.AcquireResponse()
	response.SetBodyString("private-response")
	fasthttp.ReleaseResponse(response)
	nextResponse := fasthttp.AcquireResponse()
	defer fasthttp.ReleaseResponse(nextResponse)
	if len(nextResponse.Body()) != 0 {
		t.Fatalf("new response retained body: %q", nextResponse.Body())
	}
	request := fasthttp.AcquireRequest()
	request.SetBodyString("private-request")
	fasthttp.ReleaseRequest(request)
	nextRequest := fasthttp.AcquireRequest()
	defer fasthttp.ReleaseRequest(nextRequest)
	if len(nextRequest.Body()) != 0 {
		t.Fatalf("new request retained body: %q", nextRequest.Body())
	}
}

// TestSDKGetFailureBuffer checks whether early failures preserve caller-owned content.
func TestSDKGetFailureBuffer(t *testing.T) {
	for _, sample := range []struct {
		name string
		call func([]byte) (int, []byte, error)
	}{
		{"Get", func(dst []byte) (int, []byte, error) { return fasthttp.Get(dst, "ftp://local.invalid/") }},
		{"GetTimeout", func(dst []byte) (int, []byte, error) {
			return fasthttp.GetTimeout(dst, "ftp://local.invalid/", time.Second)
		}},
		{"GetDeadline", func(dst []byte) (int, []byte, error) {
			return fasthttp.GetDeadline(dst, "ftp://local.invalid/", time.Now().Add(time.Second))
		}},
		{"Post", func(dst []byte) (int, []byte, error) { return fasthttp.Post(dst, "ftp://local.invalid/", nil) }},
	} {
		t.Run(sample.name, func(t *testing.T) {
			_, body, err := sample.call([]byte("prior-response-content"))
			if err == nil || string(body) != "prior-response-content" {
				t.Fatalf("body=%q error=%v", body, err)
			}
		})
	}
}

// TestSDKDoFailureStates distinguishes failures before and after response reset.
func TestSDKDoFailureStates(t *testing.T) {
	for _, test := range []struct{ name, uri, body string }{
		{"transport", "http://local.invalid/", ""},
		{"unsupported protocol", "ftp://local.invalid/", "prior-response-content"},
		{"missing host", "http:///", ""},
	} {
		t.Run(test.name, func(t *testing.T) {
			client := &fasthttp.Client{Dial: func(string) (net.Conn, error) { return nil, errors.New("controlled transport failure") }}
			request, response := fasthttp.AcquireRequest(), fasthttp.AcquireResponse()
			defer fasthttp.ReleaseRequest(request)
			defer fasthttp.ReleaseResponse(response)
			request.SetRequestURI(test.uri)
			response.SetBodyString("prior-response-content")
			if err := client.Do(request, response); err == nil {
				t.Fatal("failure must be reported")
			}
			if string(response.Body()) != test.body {
				t.Fatalf("failure body=%q want=%q", response.Body(), test.body)
			}
		})
	}
}

// TestSDKDoReuse checks success, early failure and a later success on the same response.
func TestSDKDoReuse(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if _, err := w.Write([]byte(r.URL.Path)); err != nil {
			t.Error(err)
		}
	}))
	defer server.Close()
	client := &fasthttp.Client{}
	request, response := fasthttp.AcquireRequest(), fasthttp.AcquireResponse()
	defer fasthttp.ReleaseRequest(request)
	defer fasthttp.ReleaseResponse(response)
	request.SetRequestURI(server.URL + "/first")
	if err := client.Do(request, response); err != nil || string(response.Body()) != "/first" {
		t.Fatalf("first response body=%q err=%v", response.Body(), err)
	}
	request.SetRequestURI("ftp://local.invalid/")
	if err := client.Do(request, response); err == nil || string(response.Body()) != "/first" {
		t.Fatalf("early failure body=%q err=%v", response.Body(), err)
	}
	request.SetRequestURI(server.URL + "/second")
	if err := client.Do(request, response); err != nil || string(response.Body()) != "/second" {
		t.Fatalf("second response body=%q err=%v", response.Body(), err)
	}
}

// TestSDKHostClientResponse checks both return and output-object bodies on a real host client.
func TestSDKHostClientResponse(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if _, err := w.Write([]byte("host-body")); err != nil {
			t.Error(err)
		}
	}))
	defer server.Close()
	address, err := url.Parse(server.URL)
	if err != nil {
		t.Fatal(err)
	}
	client := &fasthttp.HostClient{Addr: address.Host}
	status, body, err := client.Get(nil, server.URL)
	if err != nil || status != http.StatusOK || string(body) != "host-body" {
		t.Fatalf("host get status=%d body=%q err=%v", status, body, err)
	}
	request, response := fasthttp.AcquireRequest(), fasthttp.AcquireResponse()
	defer fasthttp.ReleaseRequest(request)
	defer fasthttp.ReleaseResponse(response)
	request.SetRequestURI(server.URL)
	if err := client.Do(request, response); err != nil || string(response.Body()) != "host-body" {
		t.Fatalf("host do body=%q err=%v", response.Body(), err)
	}
}
