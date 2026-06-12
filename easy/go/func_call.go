package main

import (
	"github.com/zzet/gortex/testdata/go/cmd_exec"
	"github.com/zzet/gortex/testdata/go/common"
	"github.com/zzet/gortex/testdata/go/iface"
)

func RunByChild(cmd string) string {
	child := common.VulnChild{}
	return child.Run(cmd)
}

func RunByInterface(cmd string) string {
	var runner iface.VulnRunner = cmd_exec.InterfaceImpl{}
	return runner.Run(cmd)
}
