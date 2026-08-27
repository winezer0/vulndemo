from repository import UserRepository


class UserService:
    def __init__(self, repository: UserRepository):
        self.repository = repository

    def create(self, name: str) -> None:
        self.repository.save(name)
