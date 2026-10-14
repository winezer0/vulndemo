import { Inject } from '@nestjs/common';
class Service { run(): void {} }
class Controller {
  constructor(@Inject(Service) public service: Service, @Inject('TOKEN') readonly token?: string) {}
  handle() { this.service.run(); }
}
