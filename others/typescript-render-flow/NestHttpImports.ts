import { Controller as Ctrl, Get as Fetch } from '@nestjs/common';
@Ctrl('/imported')
export class ImportedHTTPController {
  @Fetch('/item') importedHandler() {}
}
function Controller(_path: string): ClassDecorator { return () => {}; }
function Get(_path: string): MethodDecorator { return () => {}; }
@Controller('/local')
export class LocalHTTPController {
  @Get('/item') localHandler() {}
}
