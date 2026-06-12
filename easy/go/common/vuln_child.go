package common

type VulnChild struct {
	VulnParent
}

func (c VulnChild) Run(cmd string) string {
	return c.Exec(cmd)
}
