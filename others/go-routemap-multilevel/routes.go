package app

import "net/http"

func registerRoutes(mux *http.ServeMux) {
	registerUsers(mux)
	if featureEnabled() {
		mux.HandleFunc("GET "+apiPrefix+"/admin", adminHandler)
	}
}

func registerUsers(mux *http.ServeMux) {
	mux.HandleFunc("GET "+"/api"+"/users/{id}", userHandler)
}

func featureEnabled() bool {
	return true
}

func userHandler(http.ResponseWriter, *http.Request) {}

func adminHandler(http.ResponseWriter, *http.Request) {}
