import { inject } from '@angular/core';
class Service { run() {} }
class Good {
  private service = inject(Service);
  execute() { this.service.run(); }
}
function build(inject: (token: unknown) => unknown) {
  class Bad {
    private service = inject(Service);
    execute() { this.service.run(); }
  }
  return Bad;
}
