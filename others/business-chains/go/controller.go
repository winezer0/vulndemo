package business

type UserController struct {
	service *UserService
}

func (controller *UserController) Create(name string) {
	controller.service.Create(name)
}
