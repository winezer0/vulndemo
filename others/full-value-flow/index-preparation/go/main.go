package sample

import "net/http"

func Source() string { return "value" }
func Sink(string)    {}

func Driver(input string) string {
	direct := input
	complex := input + "!"
	Sink(input)
	Sink(input + "!")
	result := Source()
	_ = direct
	_ = result
	return complex
}

func Handler(w http.ResponseWriter, r *http.Request) {
	_, _ = w.Write([]byte(Driver(r.URL.Query().Get("cmd"))))
}

func Register() { http.HandleFunc("GET /api", Handler) }

func Client() { _, _ = http.Get("http://service/api") }
