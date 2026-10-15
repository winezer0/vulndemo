import { UseGuards } from '@nestjs/common';

class Imported {
  @UseGuards(AuthGuard)
  handle() {}
}

{
  const UseGuards = (_guard: unknown) => (_target: unknown) => {};
  class Shadowed {
    @UseGuards(AuthGuard)
    handle() {}
  }
}
