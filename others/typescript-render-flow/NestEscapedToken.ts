import { Inject } from '@nestjs/common';
class TokenService {}
class TokenModule {
  static forRoot() { return {providers: [
    {provide: 'WRONG\x41', useClass: TokenService},
    {provide: 'PLAIN', useClass: TokenService},
  ]}; }
}
class TokenConsumer {
  constructor(@Inject('WRONG\x41') private bad: TokenService,
              @Inject('PLAIN') private good: TokenService) {}
}
