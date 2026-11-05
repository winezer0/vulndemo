package routes

import "github.com/gin-gonic/gin"

// globalAuth supplies a same-package registration from a different file.
func globalAuth(c *gin.Context) { c.Next() }
