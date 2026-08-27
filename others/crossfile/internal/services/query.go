package services

import (
	"context"
	"database/sql"
)

// Query demonstrates a cross-file database API call.
func Query(value string) {
	db, err := sql.Open("sqlite3", ":memory:")
	if err != nil {
		return
	}
	defer db.Close()
	_, _ = db.QueryContext(context.Background(), value)
}
