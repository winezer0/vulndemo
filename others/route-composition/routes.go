package routes

import "github.com/gin-gonic/gin"

func groupAuth(c *gin.Context) { c.Next() }
func serve(c *gin.Context) { c.String(200, "ok") }

func setup() {
	root := gin.New()
	root.Use(globalAuth)
	api := root.Group("/v1", groupAuth)
	api.GET("/go", serve)
}
