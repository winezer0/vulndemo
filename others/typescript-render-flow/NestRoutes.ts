@Controller('alpha')
class Alpha { @Get('one') one() {} }
@Controller('beta')
class Beta { @Get('two') two() {} }
import { Controller, Get } from '@nestjs/common';
