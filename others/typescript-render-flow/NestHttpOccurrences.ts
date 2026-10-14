import { Controller, Get } from '@nestjs/common';

@Controller('/occurrences')
export class FirstOccurrenceController {
  @Get('/items') firstOccurrence() {}
}

@Controller('/occurrences')
export class SecondOccurrenceController {
  @Get('/items') secondOccurrence() {}
}
