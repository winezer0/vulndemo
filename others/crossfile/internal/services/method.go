package services

import "os/exec"

// Service demonstrates a cross-file receiver method chain.
type Service struct{}

// NewService constructs Service in a different file from its methods.
func NewService() *Service {
	return &Service{}
}

// Execute forwards method calls to the private receiver method.
func (s *Service) Execute(value string) {
	s.run(value)
}

func (s *Service) run(value string) {
	_ = exec.Command("sh", "-c", value).Run()
}
