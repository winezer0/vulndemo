package main

import (
	"vulndemo/easy/go/cmd_exec"
	"vulndemo/easy/go/common"
	"vulndemo/easy/go/iface"
)

func RunByChild(cmd string) string {
	child := common.VulnChild{}
	return child.Run(cmd)
}

func RunByInterface(cmd string) string {
	var runner iface.VulnRunner = cmd_exec.InterfaceImpl{}
	return runner.Run(cmd)
}
