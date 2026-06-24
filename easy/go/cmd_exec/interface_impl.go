package cmd_exec

import "vulndemo/easy/go/common"

type InterfaceImpl struct {
	common.VulnChild
}

func (i InterfaceImpl) Run(cmd string) string {
	return i.Exec(cmd)
}
