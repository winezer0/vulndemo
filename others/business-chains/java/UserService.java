package business;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void create(String name) {
        repository.save(name);
    }
}
