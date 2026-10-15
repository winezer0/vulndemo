import { Inject } from '@nestjs/common';
class Good {
  constructor(@Inject('GOOD') private value: object) {}
}
function build(Inject: (token: string) => unknown) {
  class Bad {
    constructor(@Inject('BAD') private value: object) {}
  }
  return Bad;
}
function InjectLocal(_token: string) { return (_target: unknown) => {}; }
class Local {
  constructor(@InjectLocal('LOCAL') private value: object) {}
}
