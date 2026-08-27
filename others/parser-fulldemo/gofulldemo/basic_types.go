package gofulldemo

import (
	"fmt"
	"os"
	"time"

	alias_http "net/http"
)

// Package-level variable declarations
var GlobalVersion = "1.0.0"

// Package-level typed variable
var MaxItems int = 100

// Package-level constant
const AppName = "gofulldemo"

// Constant block
const (
	StatusOK       = 200
	StatusNotFound = 404
)

// iota enumeration
type Level int

const (
	Debug Level = iota
	Info
	Warn
	Error
)

// Struct type definition
type User struct {
	ID        int
	Name      string
	email     string // Private field
	CreatedAt time.Time
}

// Embedded field
type AdminUser struct {
	User
	Role string
}

// Embedded pointer
type SuperAdmin struct {
	*User
	Permissions []string
}

// Type alias
type MyInt = int

// Newtype definition
type Score int

// Pointer newtype
type ScorePtr *Score

// Slice newtype
type Scores []Score

// Generic type
type Container[T any] struct {
	Value T
}

// Interface definition
type Reader interface {
	Read(p []byte) (int, error)
}

// Embedded interface
type ReadWriter interface {
	Reader
	Write(p []byte) (int, error)
}

// Generic interface
type Comparer[T any] interface {
	Compare(other T) int
}

// Function type definition
type HandlerFunc func(w alias_http.ResponseWriter, r *alias_http.Request)

// Private type
type internalConfig struct {
	apiKey string
}
