package app

import "github.com/labstack/echo/v4"

func serve(c echo.Context) error { return nil }
func auth(next echo.HandlerFunc) echo.HandlerFunc { return next }
func register() {
	app := echo.New()
	app.GET("/go", serve, auth, auth)
}
