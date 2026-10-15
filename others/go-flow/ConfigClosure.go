package sample

import "os"

func configClosures() { first := func() { os.Getenv("FIRST") }; second := func() { os.Getenv("SECOND") }; _, _ = first, second }
