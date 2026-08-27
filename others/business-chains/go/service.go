package business

type UserService struct {
	repository *UserRepository
}

func (service *UserService) Create(name string) {
	service.repository.Save(name)
}
