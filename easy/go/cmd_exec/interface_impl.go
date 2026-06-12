package cmd_exec

import "github.com/zzet/gortex/testdata/go/common"

type InterfaceImpl struct {
	common.VulnChild
}

func (i InterfaceImpl) Run(cmd string) string {
	return i.Exec(cmd)
}
