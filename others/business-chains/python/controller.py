from service import UserService


class UserController:
    def __init__(self, service: UserService):
        self.service = service

    def create(self, name: str) -> None:
        self.service.create(name)
