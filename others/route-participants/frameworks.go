package app

import (
    "net/http"
    "github.com/gin-gonic/gin"
    "github.com/go-chi/chi/v5"
    "github.com/gofiber/fiber/v2"
)

func registerGin() {
    r := gin.Default()
    r.GET("/gin", ginAuth, ginServe)
}
func ginAuth(c *gin.Context) { c.Next() }
func ginServe(c *gin.Context) {}
func registerChi() {
    r := chi.NewRouter()
    r.Get("/chi", httpServe)
}
func httpServe(w http.ResponseWriter, r *http.Request) {}
func registerHTTP() { http.HandleFunc("/net-http", httpServe) }
func registerFiber() {
    r := fiber.New()
    r.Get("/fiber", fiberServe)
}
func fiberServe(c *fiber.Ctx) error { return nil }
