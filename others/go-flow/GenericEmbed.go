package sample

import "time"

type GenericBox[T any] struct { Value T }
type GenericHolder struct { GenericBox[time.Time] }
