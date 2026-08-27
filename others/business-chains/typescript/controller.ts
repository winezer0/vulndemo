import { UserService } from "./service";

export class UserController {
  constructor(private readonly service: UserService) {}

  create(name: string): void {
    this.service.create(name);
  }
}
