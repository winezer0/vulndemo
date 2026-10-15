class Service {
  runConstructed() {}
}

export class Controller {
  private service = new Service();
  handle() { this.service.runConstructed(); }
}

export function shadowed(Service: new () => unknown) {
  class ShadowedController {
    private service = new Service();
    handle() { this.service.runConstructed(); }
  }
  return ShadowedController;
}
