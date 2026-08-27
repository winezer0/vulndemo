package business;

public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public void create(String name) {
        service.create(name);
    }
}
