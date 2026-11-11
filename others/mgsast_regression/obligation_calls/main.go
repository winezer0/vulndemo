package obligationcalls

import (
	archive "archive/tar"
	"database/sql"
	"io"
	"time"
)

type Reader struct{}

// Next deliberately shares the archive method's name.
func (*Reader) Next() {}

// Archive exercises imported receiver identity through a constructor and alias.
func Archive(input io.Reader) {
	reader := archive.NewReader(input)
	alias := reader
	_, _ = alias.Next()
}

// Database must not acquire archive obligations from the shared method name.
func Database(rows *sql.Rows) {
	_ = rows.Next()
}

// Local must not acquire archive obligations from a same-name local type.
func Local(reader *Reader) {
	reader.Next()
}

// Delay contains both executable and inert call-like text.
func Delay() {
	// time.Sleep(time.Hour)
	println("time.Sleep(time.Hour)")
	time.Sleep(time.Millisecond)
}
