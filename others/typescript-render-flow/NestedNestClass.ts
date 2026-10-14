import { UseGuards } from '@nestjs/common';
class AuthGuard {}
class Outer {
  build() {
    class Inner {
      @UseGuards(AuthGuard)
      handle() {}
      static forRoot() {
        return {'providers': [{'provide': 'TOKEN', 'useClass': Service}]};
      }
    }
  }
}
class Service {}
