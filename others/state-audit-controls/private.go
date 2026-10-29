package controls

import (
	"os"
	"path/filepath"
)

// WritePrivate creates a private directory and exclusively creates its final file entry.
func WritePrivate(payload []byte) error {
	directory, err := os.MkdirTemp("", "private-output-")
	if err != nil {
		return err
	}
	defer os.RemoveAll(directory)
	file, err := os.OpenFile(filepath.Join(directory, "output"), os.O_WRONLY|os.O_CREATE|os.O_EXCL, 0600)
	if err != nil {
		return err
	}
	defer file.Close()
	_, err = file.Write(payload)
	return err
}
