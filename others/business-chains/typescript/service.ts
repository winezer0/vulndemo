import { UserRepository } from "./repository";

export class UserService {
  constructor(private readonly repository: UserRepository) {}

  create(name: string): void {
    this.repository.save(name);
  }
}
