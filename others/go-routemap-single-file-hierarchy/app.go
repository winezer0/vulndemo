package app

import "net/http"

const apiPrefix = "/api"

func setup() http.Handler {
	mux := http.NewServeMux()
	registerAPI(mux)
	return mux
}

func registerAPI(mux *http.ServeMux) {
	const usersPath = apiPrefix + "/users"
	mux.HandleFunc("GET "+usersPath, usersHandler)
	mux.HandleFunc("POST "+apiPrefix+"/login", loginHandler)
}

func usersHandler(http.ResponseWriter, *http.Request) {}

func loginHandler(http.ResponseWriter, *http.Request) {}
