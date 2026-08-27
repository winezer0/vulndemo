package business

type UserRepository struct{}

func (repository *UserRepository) Save(name string) {
	println(name)
}
