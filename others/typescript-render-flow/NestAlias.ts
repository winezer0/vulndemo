import { Inject as NestInject } from '@nestjs/common';

class NestAliasController {
  constructor(@NestInject('SERVICE') private readonly service: object) {}
}

import { UseGuards as Guards } from '@nestjs/common';
class GuardedController {
  @Guards(AuthGuard)
  handle() {}
}
