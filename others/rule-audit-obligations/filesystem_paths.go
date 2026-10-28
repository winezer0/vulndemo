package obligations

import (
	"archive/tar"
	"errors"
	disk "os"
	"path/filepath"
)

// ArchiveEntryWrite exposes an archive-controlled path without using a path builder.
func ArchiveEntryWrite(root string, archive *tar.Reader) error {
	entry, err := archive.Next()
	if err != nil {
		return err
	}
	file, err := disk.Create(root + "/" + entry.Name)
	if err != nil {
		return err
	}
	return file.Close()
}

// OpenSelectedPath exposes effective flags to the verifier instead of assuming every open writes.
func OpenSelectedPath(name string, flags int) error {
	file, err := disk.OpenFile(name, flags, 0600)
	if err != nil {
		return err
	}
	return file.Close()
}

// GuardedWrite must remain an operation candidate whose effective containment can disprove the risk.
func GuardedWrite(root, name string, content []byte) error {
	if !filepath.IsLocal(name) {
		return errors.New("path escapes root")
	}
	return disk.WriteFile(filepath.Join(root, name), content, 0600)
}
