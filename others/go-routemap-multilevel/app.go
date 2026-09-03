package app

import "net/http"

type App struct {
	mux *http.ServeMux
}

const apiPrefix = "/api"

func newApp() *App {
	app := &App{}
	app.mux = http.NewServeMux()
	return app
}

func (a *App) register() {
	registerRoutes(a.mux)
	a.mux.HandleFunc("GET /health", healthHandler)
}

func setup() http.Handler {
	app := newApp()
	app.register()
	return app.mux
}

func healthHandler(http.ResponseWriter, *http.Request) {}
