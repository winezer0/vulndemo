import { Controller, Get } from '@nestjs/common';
@Controller('nest')
class Routes {
    @Get('items')
    serve() { return 'ok'; }
}
