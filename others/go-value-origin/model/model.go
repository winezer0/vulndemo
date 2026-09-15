package model

type Writer struct{}

func (w *Writer) Write(value []byte) (int, error) {
	return len(value), nil
}

type Other struct{}

func (o *Other) Write(value []byte) (int, error) {
	return len(value), nil
}

type Context struct {
	Writer *Writer
}

var DefaultContext = &Context{Writer: &Writer{}}

func NewContext() *Context {
	return &Context{Writer: &Writer{}}
}

func DefaultContext() *Context {
	return &Context{Writer: &Writer{}}
}
