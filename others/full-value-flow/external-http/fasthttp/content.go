package fastfixture

import (
	"time"

	"github.com/valyala/fasthttp"
)

func Fetch(fastURL string) {
	fastStatus, fastBody, fastError := fasthttp.Get(nil, fastURL)
	consume(fastStatus, fastBody, fastError)
}

func FetchTimeout(timeoutURL string) {
	timeoutStatus, timeoutBody, timeoutError := fasthttp.GetTimeout(nil, timeoutURL, time.Second)
	consume(timeoutStatus, timeoutBody, timeoutError)
}

func FetchDeadline(deadlineURL string) {
	deadlineStatus, deadlineBody, deadlineError := fasthttp.GetDeadline(nil, deadlineURL, time.Now())
	consume(deadlineStatus, deadlineBody, deadlineError)
}

func Send(postURL string) {
	postStatus, postBody, postError := fasthttp.Post(nil, postURL, nil)
	consume(postStatus, postBody, postError)
}

func consume(...any) {}
