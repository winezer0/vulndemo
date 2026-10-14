declare const routePath: string;
@Controller('/api')
class StaticController {
  @Get(routePath) dynamic() {}
  @Get('/bad\x41') escaped() {}
  @Get('/ok') valid() {}
}
@Controller(routePath)
class DynamicController {
  @Get('/inside') inside() {}
}
import { Controller, Get } from '@nestjs/common';
