package sample

import "os"

func configFirst() { os.Getenv("TOKEN"); os.Getenv("TOKEN") }; func configSecond() { os.Getenv("TOKEN") }
func configEscaped() { os.Getenv("PRE\x41") }
